package pojo;

import java.io.Serializable;
import java.util.List;

public class EUDataGridResult implements Serializable{
	private static final long serialVersionUID = 2678419889553541356L;
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
 
}
