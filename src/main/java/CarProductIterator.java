import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CarProductIterator implements InputIterator {
    private Iterator<CarProduct> carProductIterator;
    private CarProduct currentCarProduct;

    public CarProductIterator(Iterator<CarProduct> carProductIterator) {
        this.carProductIterator = carProductIterator;
    }

    @Override
    public long weight() {
        return (long)(1.0/(currentCarProduct.getName().split(" ").length+1)*100);
    }

    @Override
    public BytesRef payload() {
        return null;
    }

    @Override
    public boolean hasPayloads() {
        return false;
    }

    @Override
    public Set<BytesRef> contexts() {
        Set<BytesRef> nameSet = new HashSet<>();
        String name = currentCarProduct.getName();
        try {
            nameSet.add(new BytesRef(name.getBytes("UTF8")));
            return nameSet;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Couldn't convert to UTF-8");
        }

    }

    @Override
    public boolean hasContexts() {
        return false;
    }

    @Override
    public BytesRef next() {

        if (carProductIterator.hasNext()) {
            currentCarProduct = carProductIterator.next();
            try
            {
                //返回当前Product的name值，把product类的name属性值作为key
                return new BytesRef(currentCarProduct.getName().getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Couldn't convert to UTF-8",e);
            }
        } else
            {
            return null;
        }
    }
}
