public class FileLogger implements LogerInterface{
    public String getName(){
       String filePath = this.file.getPath();
       return filePath;
    }
}
