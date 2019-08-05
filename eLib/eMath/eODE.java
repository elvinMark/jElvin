package eLib.eMath;

public class eODE{
	public eVector time;
	public int states;
	public eVector initial;

	public void setup(eVector initial){
		this.states = initial.length;
		this.initial = initial;
	}
	public void mesh(eVector time_dom,int N){
		double h;
		h = (time_dom.data[1]-time_dom.data[0])/(N-1);
		time = new eVector(N);
		time.data[0] = time_dom.data[0];
		for(int i=1;i<N;i++){
			time.data[i] = time.data[i-1]+h;
		}
	}
	public eVector fun_df(eVector x,double t){
		eVector out = new eVector(x.length);
		return out;
	}
	public eMatrix solveEuler(){
		eMatrix out;
		out = new eMatrix(this.time.length,this.states);
		out.data[0] = this.initial;
		for(int i=1;i<this.time.length;i++)
			out.data[i] = out.data[i-1].add(fun_df(out.data[i-1],time.data[i-1]).times(time.data[i] - time.data[i-1]));
		return out;
	}
}