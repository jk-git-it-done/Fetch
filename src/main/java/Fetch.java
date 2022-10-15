import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Fetch {
    public static void main(String[] args) {
        InputStream inputStream = null;
        boolean showMeta = false;
        try {
            for(String arg : args){
                if (arg.compareTo("--metadata") == 0){
                    showMeta = true;
                    continue;
                }
                URL url = new URL(arg);
                String website = arg.split("://")[1];
                inputStream = url.openStream();

                File file = new File(website + ".html");
                String last_fetch = "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE LLL dd Y hh:mm z");

                if(file.exists()){
                    last_fetch = simpleDateFormat.format(file.lastModified());
                }

                file.delete();
                file.createNewFile();

                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String line;
                int num_links = 0;
                int images = 0;

                while ((line = bufferedReader.readLine()) != null) {
                    if(line.contains("<img")) images++;
                    if(line.contains("href=")) num_links++;
                    bufferedWriter.write(line);
                }
                bufferedWriter.close();

                if(showMeta){
                    System.out.println("site: " + website);
                    System.out.println("num_links: " + num_links);
                    System.out.println("images: " + images);
                    if(!last_fetch.isEmpty()) System.out.println("last_fetch: " + last_fetch);
                }

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException ioe) {}
        }
    }
}

