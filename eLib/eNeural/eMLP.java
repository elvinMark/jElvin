package eLib.eNeural;

import eLib.eMath.*;

class eLayer{
	public int inputs;
	public int outputs;
	public eMatrix w;
	public eMatrix in;
	public eMatrix out;
	public eMatrix delta;

	public eLayer(int inputs,int outputs){
		this.inputs = inputs;
		this.outputs = outputs;
		this.w = new eMatrix(inputs,outputs);
		this.w.random();
	}
	public void print(){
		System.out.println("Inputs: "+this.inputs+" "+"Outputs: "+this.outputs);
		this.w.print();
	} 
	public static eMatrix sigmoid(eMatrix A){
		eMatrix out = new eMatrix(A.rows,A.cols);
		for(int i=0;i<A.rows;i++){
			for(int j=0;j<A.cols;j++)
				out.data[i].data[j] = 1/(1+Math.exp(-A.data[i].data[j]));
		}
		return out;
	}
	public void forward(eMatrix in_set){
		this.in = in_set.copy();
		this.out = sigmoid(in_set.dot(this.w));
	}
	public eMatrix backward(eMatrix err){
		eMatrix ones;
		ones = new eMatrix(err.rows,err.cols);
		ones.ones();
		this.delta = this.out.times(ones.diff(this.out)).times(err);
		return this.delta.dot(this.w.transpose());
	}
	public void update(){
		this.w = this.w.diff(this.in.transpose().dot(this.delta));
	}
}

public class eMLP{
	public int[] struct;
	public int num_layers;
	public eLayer[] layers;

	public eMLP(int[] struct){
		this.struct = struct;
		this.num_layers = struct.length-1;
		this.layers = new eLayer[struct.length-1];
		for(int i=0;i<struct.length-1;i++)
			this.layers[i] = new eLayer(struct[i],struct[i+1]);
	}
	public void print(){
		for(int i = 0;i<this.num_layers;i++){
			System.out.println("Layer: "+ (i+1));
			this.layers[i].print();
		}
	}
	public eMatrix forward(eMatrix in_set){
		eMatrix aux;
		eMatrix out;
		aux = in_set;
		for(int i = 0;i<this.num_layers;i++){
			this.layers[i].forward(aux);
			aux = this.layers[i].out;
		}
		out = this.layers[this.num_layers-1].out.copy();
		return out;
	}
	public void backward(eMatrix err){
		eMatrix aux;
		aux = err;
		for(int i=this.num_layers-1;i>=0;i--){
			aux = this.layers[i].backward(aux);
		}
	}
	public void update(){
		for(int i=0;i<this.num_layers;i++)
			this.layers[i].update();
	}
	public void train(eMatrix in_set,eMatrix out_set,int N){
		eMatrix out;
		eMatrix err;
		for(int i=0;i<N;i++){
			out =this.forward(in_set);
			err = out.diff(out_set);
			this.backward(err);
			this.update();
		}
	}
}