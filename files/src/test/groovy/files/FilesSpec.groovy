package files

import spock.lang.Specification
import java.util.concurrent.Executors
import java.util.concurrent.ExecutionException
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class FilesSpec extends Specification {

    def work = new File(new File(System.getProperty('java.io.tmpdir')), 'filesspec')

    def setup() {
        work.mkdirs()
    }

    def cleanup() {
        work.deleteDir()
    }

    def "verify if the parent tree exists"() {
        given:
        def file = new File(work, "subdir/test")
        file.parentFile.mkdirs()

        expect:
        new BadFileHandler(file).parentTreeExists
    }

    def "verify if a dir doesn't exist"() {
        expect:
        !new BadFileHandler(new File(work, "subdir/test")).parentTreeExists
    }

    def "create a directory tree"() {
        given:
        def file = new File(work, "subdir/test")

        when:
        new BadFileHandler(file).mkdirs()

        then:
        file.parentFile.exists()
        file.parentFile.directory
        !file.exists()
    }

    def "if directory tree exists throw exception"() {
        given:
        def writer = new BadFileHandler(new File(work, "subdir/test"))

        when:
        writer.mkdirs()
        writer.mkdirs()

        then:
        thrown(IOException)
    }

    def "write string to file"() {
        given:
        def file = new File(work, 'test')
        assert !file.exists()

        when:
        new BadFileHandler(file).write('some test content')

        then:
        file.text == 'some test content'
    }

    def "write file while creating parent tree"() {
        given:
        def file = new File(work, 'subdir/test')
        assert !file.parentFile.exists()

        when:
        new FileWriter(new BadFileHandler(file)).write('some test content')

        then:
        file.text == 'some test content'
    }

    def "BadFileHandler will throw an exception if used multi-threaded"() {
        given:
        def handler1 = new BadFileHandler(new File(work, 'subdir/file1'))
        def handler2 = new BadFileHandler(new File(work, 'subdir/file2'))

        when:
        createParentTreeConcurrently(handler1, handler2)

        then:
        thrown(ExecutionException)
    }

    def "BetterFileHandler will manage if used multi-threaded"() {
        given:
        def handler1 = new BetterFileHandler(new File(work, 'subdir/file1'))
        def handler2 = new BetterFileHandler(new File(work, 'subdir/file2'))

        expect:
        createParentTreeConcurrently(handler1, handler2)
    }

    private def createParentTreeConcurrently(FileHandler handler1, FileHandler handler2) {
        def h1 = new BlockingFileHandler(handler1)
        def h2 = new BlockingFileHandler(handler2)
        withExecutor {ExecutorService executor ->
            List futures = useHandlersConcurrently(executor, h1, h2)

            h1.waitForParentTreeCheck()
            h2.waitForParentTreeCheck()
            h1.tryCreatingParentTree()
            h2.tryCreatingParentTree()

            checkForExceptions(futures)
        }
    }

    private def checkForExceptions(List<Future> futures) {
        futures*.get()
    }

    private List<Future> useHandlersConcurrently(ExecutorService executor, BlockingFileHandler h1, BlockingFileHandler h2) {
        def task = {handler -> return {new FileWriter(handler).write('whatever')} as Callable}
        def futures = [task(h1), task(h2)].collect {executor.submit(it)}
        return futures
    }

    private def withExecutor(Closure task) {
        def executor = Executors.newFixedThreadPool(2)
        try {
            task(executor)
        } finally {
            executor.shutdown()
        }
    }

    private static final class BlockingFileHandler implements FileHandler {
        private final FileHandler delegate
        private final def checkedForParentTree = new CountDownLatch(1)
        private final def doMkdirs = new CountDownLatch(1)

        BlockingFileHandler(FileHandler delegate) {
            this.delegate = delegate
        }

        String write(String msg) {
            delegate.write msg
        }

        void mkdirs() {
            doMkdirs.await()
            delegate.mkdirs()
        }

        boolean isParentTreeExists() {
            def exists = delegate.parentTreeExists
            checkedForParentTree.countDown()
            exists
        }

        def waitForParentTreeCheck() {
            checkedForParentTree.await()
        }

        def tryCreatingParentTree() {
            doMkdirs.countDown()
        }
    }
}
