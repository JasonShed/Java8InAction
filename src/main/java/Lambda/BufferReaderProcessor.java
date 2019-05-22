package Lambda;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 第1步：定义函数式接口
 */
@FunctionalInterface
public interface BufferReaderProcessor {
    String process(BufferedReader br) throws IOException;
}
