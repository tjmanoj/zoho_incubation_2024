class FileManager {
    String name;
    Directory parent;

    public FileManager(String name) {
        this.name = name;
    }
}
class Directory extends FileManager {
    static Directory trash;
    Directory[] subDirectories;
    File[] filesList;
    static String[] curLocation;
    int trashInd = 0;

    public Directory(String name) {
        super(name);
        subDirectories = new Directory[10];
        filesList = new File[10];
    }

    public static Directory CMD() {
        Directory.curLocation = new String[100];
        Directory root = new Directory("~");
        Directory.trash = new Directory("trash");
        root.subDirectories[0] = new Directory("c:\\");
        root = root.subDirectories[0];
        Directory.curLocation[0] = "c:";
        return root;
    }

    public void pwd() {
        printWorkingLocation(Directory.curLocation);
    }

    private static void printWorkingLocation(String[] location) {
        for (String locationPart : location) {
            if (locationPart == null)
                break;
            System.out.print(locationPart + "\\");
        }
        System.out.println();
    }

    public Directory cd(String directoryName) {
        if (directoryName.equals("..")) {
            return cdUp();
        } else if (directoryName.equals("//")) {
            return goHome();
        }
        return getSubDirectory(directoryName);
    }

    private Directory getSubDirectory(String directoryName) {
        for (Directory directory : subDirectories) {
            if (directory != null && directory.name.equals(directoryName)) {
                Directory.moveLocationFront(directoryName);
                return directory;
            }
        }
        System.out.println("Directory not found");
        return this;
    }

    private static void moveLocationFront(String directoryName) {
        Directory.curLocation[getLengthOfDirectory()] = directoryName;
    }

    public void mkdir(String directoryName) {
        if (isDirectoryExists(directoryName)) {
            return;
        }
        Directory directory = new Directory(directoryName);
        directory.parent = this;
        this.subDirectories[getDirectoryLength()] = directory;
        System.out.println("Directory " + directory.name + " has been created");
    }

    private int getDirectoryLength() {
        int index = 0;
        for (Directory directory : subDirectories) {
            if (directory == null) {
                return index;
            }
            index++;
        }
        return index;
    }

    private Directory cdUp() {
        Directory.moveBack();
        return this.parent;
    }

    private static void moveBack() {
        Directory.curLocation[getLengthOfDirectory() - 1] = null;
    }

    private static int getLengthOfDirectory() {
        int index = 0;
        for (String location : Directory.curLocation) {
            if (location == null) {
                break;
            }
            index++;
        }
        return index;
    }

    public void touch(String fileName) {
        File file = new File(fileName);
        file.parent = this;
        this.filesList[getFilesLength()] = file;
    }

    private int getFilesLength() {
        int index = 0;
        for (File file : this.filesList) {
            if (file == null) {
                return index;
            }
            index++;
        }
        return index;
    }

    public void dir() {
        for (Directory directory : this.subDirectories) {
            if (directory != null) {
                Directory.curLocation[getLengthOfDirectory()] = directory.name;
                printWorkingLocation(Directory.curLocation);
                Directory.curLocation[getLengthOfDirectory() - 1] = null;
            }
        }
        for (File file : this.filesList) {
            if (file != null) {
                Directory.curLocation[Directory.getLengthOfDirectory()] = file.name;
                printWorkingLocation(Directory.curLocation);
                Directory.curLocation[getLengthOfDirectory() - 1] = null;
            }
        }
    }

    public Directory goHome() {
        Directory currentDirectory = this;
        int index = Directory.getLengthOfDirectory() - 1;
        while (currentDirectory.parent != null && !currentDirectory.name.equals("c:\\")) {
            currentDirectory = currentDirectory.parent;
            Directory.curLocation[index--] = null;
        }
        return currentDirectory;
    }

    public void renameFile(String currentFileName, String newFileName) {
        if (isFileExists(newFileName)) {
            return;
        }
        File file = getFile(currentFileName);
        if (file != null) {
            file.name = newFileName;
        }
    }

    public void renameDir(String currentDirName, String newDirName) {
        if (isDirectoryExists(newDirName)) {
            return;
        }
        Directory directory = getSubDirectory(currentDirName);
        if (directory != null) {
            directory.name = newDirName;
            Directory.curLocation[getLengthOfDirectory() - 1] = newDirName;
        }
    }

    private File getFile(String currentFileName) {
        for (File file : this.filesList) {
            if (file != null && file.name.equals(currentFileName)) {
                return file;
            }
        }
        return null;
    }

    public void deleteFile(String fileName) {
        for (int i = 0; i < filesList.length; i++) {
            if (filesList[i] != null && filesList[i].name.equals(fileName)) {
                filesList[i] = null;
                System.out.println("File " + fileName + " has been deleted");
                return;
            }
        }
        System.out.println("File " + fileName + " not found");
    }

    public void deleteDirectory(String directoryName) {
        for (int i = 0; i < subDirectories.length; i++) {
            if (subDirectories[i] != null && subDirectories[i].name.equals(directoryName)) {
                trash.subDirectories[trashInd++] = subDirectories[i];
                subDirectories[i] = null;
                System.out.println("Directory " + directoryName + " has been deleted");
                return;
            }
        }
        System.out.println("Directory " + directoryName + " not found");
    }

    public void restoreFile(String fileName) {
        if (trash == null) {
            System.out.println("Trash is empty");
            return;
        }
        File file = trash.getFile(fileName);
        if (file != null) {
            this.filesList[getFilesLength()] = file;
            System.out.println("File " + fileName + " has been restored");
        } 
        else {
            System.out.println("File " + fileName + " not found in trash");
        }
    }

    public void restoreDirectory(String directoryName) {
        if (trash == null) {
            System.out.println("Trash is empty");
            return;
        }
        Directory directory = trash.getSubDirectory(directoryName);
        if (directory != null) {
            this.subDirectories[getDirectoryLength()] = directory;
            System.out.println("Directory " + directoryName + " has been restored");
        } 
        else {
            System.out.println("Directory " + directoryName + " not found in trash");
        }
    }

    public void moveFile(String fileName, Directory newDirectory) {
        File file = getFile(fileName);
        if (file != null) {
            deleteFile(fileName);
            newDirectory.touch(fileName);
            System.out.println("File " + fileName + " has been moved to " + newDirectory.name);
        } 
        else {
            System.out.println("File " + fileName + " not found");
        }
    }

    public void moveDirectory(String directoryName, Directory newDirectory) {
        Directory directory = getSubDirectory(directoryName);
        if (directory != null) {
            deleteDirectory(directoryName);
            newDirectory.mkdir(directoryName);
            newDirectory.getSubDirectory(directoryName).parent = newDirectory;
            System.out.println("Directory " + directoryName + " has been moved to " + newDirectory.name);
        } 
        else {
            System.out.println("Directory " + directoryName + " not found");
        }
    }

    private boolean isDirectoryExists(String newName) {
        for (Directory directory : this.subDirectories) {
            if (directory != null && directory.name.equals(newName)) {
                System.out.println("Directory already exists");
                return true;
            }
        }
        return false;
    }

    private boolean isFileExists(String newName) {
        for (File file : this.filesList) {
            if (file != null && file.name.equals(newName)) {
                System.out.println("File already exists");
                return true;
            }
        }
        return false;
    }
}

class File extends FileManager {
    String format;
    String size;
    String timeCreated;

    public File(String fileName) {
        super(fileName);
    }
}

public class Main {
    public static void main(String[] args) {
        Directory root = Directory.CMD();
        root.mkdir("Courses");
        root = root.cd("Courses");

        root.mkdir("Java");
        root.mkdir("Python");
        root.mkdir("C");
        root = root.cd("Java");
        root.mkdir("Videos");
        root.mkdir("Files");
        root = root.cd("Videos");
        root.touch("video1");
        root.touch("video2");
        root.touch("video3");
        root.dir();
        root = root.cd("..");
        root = root.cd("..");

        root.renameDir("VIDEO", "Video");

        root.pwd();

        root.deleteDirectory("C");
        
        
        root.restoreFile("video1"); 
        root.restoreDirectory("C"); 
        root.deleteFile("video1");

        root.moveFile("video2", root.cd("Files"));
        root.moveDirectory("Python", root.cd("Files"));

        root.pwd();
    }
}

