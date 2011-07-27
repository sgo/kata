package files

final class BetterFileHandler implements FileHandler {

    private final File file

    BetterFileHandler(File file) {
        this.file = file
    }

    String write(String msg) {
        return file.text = msg
    }

    void mkdirs() {
        if(!file.parentFile.mkdirs() && !parentTreeExists) throw new IOException("dir already exists")
    }

    boolean isParentTreeExists() {
        return file.parentFile.exists()
    }
}
