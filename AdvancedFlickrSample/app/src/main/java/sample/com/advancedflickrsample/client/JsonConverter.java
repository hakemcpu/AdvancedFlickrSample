package sample.com.advancedflickrsample.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import sample.com.advancedflickrsample.client.parsers.ApiParser;
import sample.com.advancedflickrsample.client.parsers.FlickrServiceParser;

/**
 * Created by hzaied on 3/27/15.
 */
public class JsonConverter<T> implements Converter {

    ApiParser<T> mParser;

    public JsonConverter(ApiParser<T> mParser) {
        this.mParser = mParser;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            InputStream in = body.in(); // convert the typedInput to String
            String string = fromStream(in);
            in.close(); // we are responsible to close the InputStream after use

            if (String.class.equals(type)) {
                return string;
            } else {
                return mParser.parseList(string);
            }
        } catch (Exception e) { // a lot may happen here, whatever happens
            throw new ConversionException(e); // wrap it into ConversionException so retrofit can process it
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }

    private String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append("\r\n");
        }
        return out.toString();
    }
}
