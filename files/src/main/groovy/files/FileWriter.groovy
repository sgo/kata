package files

final class FileWriter {

    private final FileHandler handler

    FileWriter(FileHandler handler) {
        this.handler = handler
    }

    def write(String msg) {
        if(!handler.parentTreeExists)
            handler.mkdirs()
        handler.write(msg)
    }
}
