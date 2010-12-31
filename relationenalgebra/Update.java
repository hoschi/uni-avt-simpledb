package relationenalgebra;

import java.util.List;
import java.util.Set;

public class Update extends Delete {

    protected List<String> columnNames;
    protected List<String> values;

    @Override
    public Set<String> getAttributes() {
        Set<String> result = super.getAttributes();
        result.addAll(columnNames);
        return result;
    }

}
