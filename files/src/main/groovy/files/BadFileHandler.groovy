package files


final class BadFileHandler implements FileHandler {

    private final File file

    BadFileHandler(File file) {
        this.file = file
    }

    String write(String msg) {
        return file.text = msg
    }

    void mkdirs() {
        if(!file.parentFile.mkdirs()) throw new IOException("dir already exists")
    }

    boolean isParentTreeExists() {
        return file.parentFile.exists()
    }
}
