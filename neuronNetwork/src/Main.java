import java.util.*;

public class Main {
    public static void main(String[] args) {
       Network net = new Network(args);
       net.start();

       while(true) {
           System.out.println("\nEnter the text you want to have labeled.");
           Scanner scan = new Scanner(System.in);
           String resp = scan.next();
           resp = resp.replaceAll("[^a-zA-Z_]", "").toLowerCase();

           List<Double> vec = new ArrayList<>();
           HashMap<Character, Integer> map = new HashMap<>();
           for (char ch = 'a'; ch <= 'z'; ++ch) {
               map.put(ch, 0);
           }

           char[] contentArr = resp.toCharArray();

           for (char c : contentArr) {
               if (map.containsKey(c)) {
                   map.replace(c, map.get(c) + 1);
               }
           }
           for (Map.Entry<Character, Integer> entry : map.entrySet()) {
               vec.add(Double.valueOf(entry.getValue()));
           }
           Dummy d = new Dummy(vec);

           net.classify(d);

           System.out.println(d.name);
       }

    }
}