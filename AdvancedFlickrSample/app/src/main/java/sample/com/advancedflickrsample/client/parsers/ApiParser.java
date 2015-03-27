package sample.com.advancedflickrsample.client.parsers;

import java.util.List;

/**
 * Created by hzaied on 3/27/15.
 */
public abstract class ApiParser<T> {

    public abstract T parse(String data);
    public abstract List<T> parseList(String data);
}
