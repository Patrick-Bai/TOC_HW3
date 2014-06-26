/*
系級:資訊103
姓名:白孟哲
學號:F74996065
*/

import java.io.*;
import java.net.URL;
import java.util.regex.*;
import java.util.Scanner;
import org.json.*;

public class TocHw3 {
          public static void main(String[] args) throws IOException, JSONException {

                int chunksize = 4096;
                byte[] chunk = new byte[chunksize];
                int count1;
                try  {
                    URL pageUrl = new URL(args[0]);

                    // 讀入網頁(位元串流)
                    BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("URL1.txt", false));
                    System.out.println("read1() running " );
                    while ((count1 = bis.read(chunk, 0, chunksize)) != -1) {
                        bos.write(chunk, 0, count1); // 寫入檔案
                    }
                    bos.close();
                    bis.close();

                  System.out.println("Done");
                 }catch (IOException e) {
                     e.printStackTrace();
              }
                String counties = args[1];
                //Scanner scan=new Scanner(System.in);
                //System.out.println("Please input year:");
                //int year;
                //year = scan.nextInt();
                //System.out.println("Please input counties:");
                //String counties = scan.nextLine();
                String road = args[2];
                //System.out.println("Please input road:");
                //String road = scan.nextLine();
                int year = Integer.parseInt(args[3]);
                /*System.out.println("Please input year:");
                int year;
                year = scan.nextInt();*/
                long sum = 0;
                int count = 0;
                JSONArray JSONFile = new JSONArray(new JSONTokener(new FileReader(new File("URL1.txt"))));
                Pattern countiesPattern = Pattern.compile(counties);
                Pattern roadPattern = Pattern.compile(road);
                Matcher countiesMatcher;
                Matcher roadMatcher;
                for(int i = 0;i < JSONFile.length();i ++){//從JSON中找到符合條件的資料
                 countiesMatcher = countiesPattern.matcher((CharSequence) JSONFile.getJSONObject(i).get("鄉鎮市區"));
                 while(countiesMatcher.find()){
                   roadMatcher = roadPattern.matcher((CharSequence) JSONFile.getJSONObject(i).get("土地區段位置或建物區門牌"));
                   while(roadMatcher.find()){
                   if(JSONFile.getJSONObject(i).getInt("交易年月") >= year*100){//在指定的年份之後的資料  就print出來
                        System.out.print(JSONFile.getJSONObject(i).get("鄉鎮市區")+ "   ");
                        System.out.print(JSONFile.getJSONObject(i).get("土地區段位置或建物區門牌")+ "   ");
                        System.out.print(JSONFile.getJSONObject(i).get("交易年月")+ "   ");
                        System.out.println(JSONFile.getJSONObject(i).get("總價元"));
                        sum += JSONFile.getJSONObject(i).getInt("總價元");
                        count ++;
                        break;
                        }
                  }
                 }
                }
                        //System.out.println("Sum = "+ sum);
               System.out.println("Average : "+ sum/count);

          }
}

