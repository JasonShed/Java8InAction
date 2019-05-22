package Lambda;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundMode {
    /**
     * 原始代码
     * @return
     * @throws IOException
     */
    public static String processFile() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return "null";
        }
    }

    /**
     * 第2步：执行一个行为，参数为函数式接口的一个实例
     *
     * @param p
     * @return
     * @throws IOException
     */
    public static String processFile(BufferReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    /**
     * 第3步：传递Lambda表达式
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            //读取1行数据
            String process1 = processFile((BufferedReader br) -> br.readLine());
            //读取2行数据
            String process2 = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
