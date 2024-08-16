class FileSystem {
    private Directory root;
    private Directory currentDir;

    public FileSystem() {
        this.root = new Directory("C:", null);
        this.currentDir = root;
    }

    public void mkdir(String name) {
        Directory newDir = new Directory(name, currentDir);
        currentDir.addSubDirectory(newDir);
        System.out.println("Directory '" + name + "' has been created.");
    }

    public void changeDir(String name) {
        if (name.equals("..")) {
            cdUp();
        } 
        else if (name.equals("/")) {
            currentDir = root;
            System.out.println("Changed to root directory.");
        } 
        else {
            Directory subDir = currentDir.findSubDirectory(name);
            if (subDir != null) {
                currentDir = subDir;
                System.out.println("Changed to directory: " + name);
            } else {
                System.out.println("Directory not found: " + name);
            }
        }
    }

    private void cdUp() {
        if (currentDir.getParent() != null) {
            currentDir = currentDir.getParent();
            System.out.println("Changed to parent directory.");
        } 
        else {
            System.out.println("Already at root directory.");
        }
    }

    public void printWorkingDir() {
        String resPath = currentDir.getPath();

        if (resPath.equals("C:")) {
            System.out.println("Current Directory: C:/");
        } 
        else {
            System.out.println("Current Directory: " + currentDir.getPath());
        }
    }

    public void dir() {
        String[] subDirectories = currentDir.getSubDirectories();
        if (subDirectories.length == 0) {
            System.out.println("No subdirectories found.");
        } 
        else {
            System.out.println("Subdirectories:");
            for (String subDir : subDirectories) {
                System.out.println("- " + subDir);
            }
        }
    }

    public void list() {
        String[] files = currentDir.getFiles();
        if (files.length == 0) {
            System.out.println("No files found.");
        } 
        else {
            System.out.println("Files:");
            for (String file : files) {
                System.out.println("- " + file);
            }
        }
    }

    public void touch(String name) {
        File newFile = new File(name);
        currentDir.addFile(newFile);
        System.out.println("File '" + name + "' has been created.");
    }

    public void renameCurrentDirectory(String newName) {
        Directory parentDir = currentDir.getParent();
        if (parentDir != null) {
            currentDir.setName(newName);
            updatePaths(parentDir);
            System.out.println("Current directory has been renamed to '" + newName + "'.");
        } 
        else {
            System.out.println("Cannot rename root directory.");
        }
    }

    public void renameFile(String oldName, String newName) {
        File file = currentDir.findFile(oldName);
        if (file != null) {
            file.setName(newName);
            System.out.println("File '" + oldName + "' has been renamed to '" + newName + "'.");
        } 
        else {
            System.out.println("File not found: " + oldName);
        }
    }

    public void deleteFile(String name) {
        boolean removed = currentDir.removeFile(name);
        if (removed) {
            System.out.println("File '" + name + "' has been deleted.");
        } 
        else {
            System.out.println("File not found: " + name);
        }
    }

    public void deleteDirectory(String name) {
        Directory dir = currentDir.findSubDirectory(name);
        if (dir != null && dir.getSubDirectoriesObjects().length == 0 && dir.getFiles().length == 0) {
            currentDir.removeSubDirectory(name);
            System.out.println("Directory '" + name + "' has been deleted.");
        } 
        else if (dir != null) {
            System.out.println("Directory '" + name + "' is not empty. Remove its contents first.");
        } 
        else {
            System.out.println("Directory not found: " + name);
        }
    }

    private void updatePaths(Directory dir) {
        Directory[] subDirs = dir.getSubDirectoriesObjects();
        for (int i = 0; i < subDirs.length; i++) {
            subDirs[i].updatePath();
            updatePaths(subDirs[i]);
        }
    }
}


class Directory {
    private String name;
    private Directory parent;
    private Directory[] subDirectories;
    private File[] files;
    private int subDirCount;
    private int fileCount;
    private String path;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.subDirectories = new Directory[100];
        this.files = new File[100];
        this.subDirCount = 0;
        this.fileCount = 0;
        updatePath();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updatePath();
    }

    public Directory getParent() {
        return parent;
    }

    public String[] getSubDirectories() {
        String[] result = new String[subDirCount];
        for (int i = 0; i < subDirCount; i++) {
            result[i] = subDirectories[i].getName();
        }
        return result;
    }

    public Directory[] getSubDirectoriesObjects() {
        Directory[] result = new Directory[subDirCount];
        for (int i = 0; i < subDirCount; i++) {
            result[i] = subDirectories[i];
        }
        return result;
    }

    public void addSubDirectory(Directory dir) {
        if (subDirCount < subDirectories.length) {
            subDirectories[subDirCount++] = dir;
        } 
        else {
            System.out.println("Directory limit reached.");
        }
    }

    public void removeSubDirectory(String name) {
        for (int i = 0; i < subDirCount; i++) {
            if (subDirectories[i].getName().equals(name)) {
                // Shift remaining directories
                for (int j = i; j < subDirCount - 1; j++) {
                    subDirectories[j] = subDirectories[j + 1];
                }
                subDirCount--;
                return;
            }
        }
    }

    public Directory findSubDirectory(String name) {
        for (int i = 0; i < subDirCount; i++) {
            if (subDirectories[i].getName().equals(name)) {
                return subDirectories[i];
            }
        }
        return null;
    }

    public String[] getFiles() {
        String[] result = new String[fileCount];
        for (int i = 0; i < fileCount; i++) {
            result[i] = files[i].getName();
        }
        return result;
    }

    public void addFile(File file) {
        if (fileCount < files.length) {
            files[fileCount++] = file;
        } 
        else {
            System.out.println("File limit reached.");
        }
    }

    public boolean removeFile(String name) {
        for (int i = 0; i < fileCount; i++) {
            if (files[i].getName().equals(name)) {
                // Shift remaining files
                for (int j = i; j < fileCount - 1; j++) {
                    files[j] = files[j + 1];
                }
                fileCount--;
                return true;
            }
        }
        return false;
    }

    public File findFile(String name) {
        for (int i = 0; i < fileCount; i++) {
            if (files[i].getName().equals(name)) {
                return files[i];
            }
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public void updatePath() {
        if (parent == null) {
            path = name;
        } else {
            path = parent.getPath() + "/" + name;
        }
    }
}

class File {
    private String name;

    public File(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        fs.mkdir("Courses");
        fs.changeDir("Courses");

        fs.mkdir("Java");
        fs.changeDir("Java");

        fs.mkdir("Module1");
        fs.mkdir("Module2");

        fs.dir();  // dir => list Directories
        fs.changeDir("Module2");

        fs.renameCurrentDirectory("module");
        fs.printWorkingDir();

        fs.mkdir("Videos");
        fs.mkdir("Audios");
        fs.changeDir("Videos");

        fs.printWorkingDir();
        fs.changeDir("..");
        fs.deleteDirectory("Videos");

        fs.touch("script.txt");

        fs.list();

        fs.deleteFile("script.txt");
        fs.changeDir("/");

        fs.printWorkingDir();

        fs.changeDir("Courses");
        fs.changeDir("Java");
        fs.changeDir("module");
        
        fs.printWorkingDir();
        
        fs.dir();
    }
}



