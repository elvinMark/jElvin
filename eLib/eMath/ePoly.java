package eLib.eMath;

public class ePoly{
	public eVector coeff;
	public int degree;

	public ePoly(int degree){
		coeff = new eVector(degree + 1);
		coeff.ones();
		this.degree = degree;
	}
	public ePoly(String p,int degree){
		this.degree = degree;
		this.coeff = new eVector(p,degree+1);
	}
	public void print(){
		String s="";
		for(int i =0;i<this.degree;i++)
			s = s + "("+this.coeff.data[i]+")" + "x^" + i + " + ";
		s = s + "("+this.coeff.data[this.degree]+")" + "x^" + this.degree;
		System.out.println(s);
	}
	public void zeros(){
		this.coeff.zeros();
	}
	public void ones(){
		this.coeff.ones();
	}
	public void copy(){
		ePoly out;
		out = new ePoly(this.degree);
		out.coeff = this.coeff.copy();
	}
	public ePoly add(ePoly p){
		ePoly out;
		if(this.degree > p.degree){
			out = new ePoly(this.degree);
			for(int i = 0;i<=this.degree;i++){
				if(i<=p.degree)
					out.coeff.data[i] = this.coeff.data[i] + p.coeff.data[i];
				else
					out.coeff.data[i] = this.coeff.data[i];
			}
		}
		else{
			out = new ePoly(p.degree);
			for(int i = 0;i<=p.degree;i++){
				if(i<=this.degree)
					out.coeff.data[i] = this.coeff.data[i] + p.coeff.data[i];
				else
					out.coeff.data[i] = p.coeff.data[i];
			}
		}
		return out;
	}
	public ePoly diff(ePoly p){
		ePoly out;
		if(this.degree > p.degree){
			out = new ePoly(this.degree);
			for(int i = 0;i<=this.degree;i++){
				if(i<=p.degree)
					out.coeff.data[i] = this.coeff.data[i] - p.coeff.data[i];
				else
					out.coeff.data[i] = this.coeff.data[i];
			}
		}
		else{
			out = new ePoly(p.degree);
			for(int i = 0;i<=p.degree;i++){
				if(i<=this.degree)
					out.coeff.data[i] = this.coeff.data[i] - p.coeff.data[i];
				else
					out.coeff.data[i] = -p.coeff.data[i];
			}
		}
		return out;
	}
	public ePoly times(ePoly p){
		ePoly out;
		out = new ePoly(this.degree+p.degree);
		out.zeros();
		for(int i = 0;i<=this.degree;i++){
			for(int j = 0;j<=p.degree;j++)
				out.coeff.data[i + j] = out.coeff.data[i + j] + this.coeff.data[i]*p.coeff.data[j];
		}
		return out;
	}
	public ePoly times(double s){
		ePoly out;
		out = new ePoly(this.degree);	
		for(int i = 0;i<=this.degree;i++)
			out.coeff.data[i] = s*this.coeff.data[i];
		return out;
	}
	public static ePoly interpolation(eVector x,eVector y){
		ePoly out,aux,p;
		double s;
		int N;
		N = x.length;
		out = new ePoly(0);
		out.zeros();
		p = new ePoly(1);
		s = 0;
		for(int i = 0;i<N;i++){
			aux = new ePoly(0);
			s = 1;
			for(int j = 0;j<N;j++){
				if(i!=j){
					p.coeff.data[0] = -x.data[j];
					aux = aux.times(p);
					s = s*(1/(x.data[i]-x.data[j]));
				}
			}
			out = out.add(aux.times(y.data[i]*s));
		}
		return out;
	}
}