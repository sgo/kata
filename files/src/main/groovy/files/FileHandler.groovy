package files


interface FileHandler {
    String write(String msg)

    void mkdirs()

    boolean isParentTreeExists()
}