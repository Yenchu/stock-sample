package idv.samples.to;

public class Index {
	//数据含义分别为：指数名称，当前点数，当前价格，涨跌率，成交量（手），成交额（万元）；
	//var hq_str_s_sh000001="上证指数,2240.030,-1.238,-0.06,768345,5962064";

	private String name;
	
	private float point;
	
	private float price;
	
	private float change;
	
	private int volumn;
	
	private long value;
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("name=").append(name);
		buf.append(", point=").append(point);
		buf.append(", price=").append(price);
		buf.append(", change=").append(change);
		buf.append(", volumn=").append(volumn);
		buf.append(", value=").append(value);
		return buf.toString();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public int getVolumn() {
		return volumn;
	}

	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
