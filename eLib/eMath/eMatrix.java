package eLib.eMath;

import java.util.*;
import java.io.*;

public class eMatrix{
	public int rows;
	public int cols;
	public eVector[] data;
	
	public eMatrix(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.data = new eVector[rows];
		for(int i = 0;i<this.rows;i++)
			this.data[i] = new eVector(this.cols);
	}
	public eMatrix(String data,int rows,int cols){
		StringTokenizer st;
		String token;
		this.rows = rows;
		this.cols = cols;
		this.data = new eVector[this.rows];
		st = new StringTokenizer(data,";");
		for(int i = 0;i<this.rows;i++){
			token = st.nextToken();
			this.data[i] = new eVector(token,this.cols);
		}
	}
	public eMatrix(double[][] data,int rows,int cols){
		this.rows = rows;
		this.cols = cols;
		this.data = new eVector[rows];
		for(int i = 0;i<this.rows;i++)
			this.data[i] = new eVector(data[i],cols);
	}
	public void print(){
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				System.out.print(this.data[i].data[j] + " ");
			System.out.println("");
		}
	}
	public void random(){
		Random rand = new Random();
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				this.data[i].data[j] = rand.nextDouble();
		}
	}
	public void ones(){
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				this.data[i].data[j] = 1;
		}
	}
	public void zeros(){
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				this.data[i].data[j] = 0;
		}
	}
	public void eye(){
		this.zeros();
		for(int i = 0;i<this.rows;i++)
			this.data[i].data[i] = 1;
	}
	public eMatrix copy(){
		eMatrix A;
		A = new eMatrix(this.rows,this.cols);
		for(int i = 0;i<this.rows;i++){
			for(int j=0;j<this.cols;j++)
				A.data[i].data[j] = this.data[i].data[j];
		}
		return A;
	}
	//Basic Operations
	public eMatrix add(eMatrix M){
		eMatrix out = new eMatrix(this.rows,this.cols);
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				out.data[i].data[j] = this.data[i].data[j] + M.data[i].data[j];
		}
		return out;
	}
	public eMatrix diff(eMatrix M){
		eMatrix out = new eMatrix(this.rows,this.cols);
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				out.data[i].data[j] = this.data[i].data[j] - M.data[i].data[j];
		}
		return out;
	}
	public eMatrix times(eMatrix M){
		eMatrix out = new eMatrix(this.rows,this.cols);
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				out.data[i].data[j] = this.data[i].data[j] * M.data[i].data[j];
		}
		return out;
	}
	public eMatrix times(double num){
		eMatrix out = new eMatrix(this.rows,this.cols);
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<this.cols;j++)
				out.data[i].data[j] = num*this.data[i].data[j];
		}
		return out;
	}
	
	public eMatrix dot(eMatrix M){
		eMatrix out = new eMatrix(this.rows,M.cols);
		double s;
		for(int i = 0;i<this.rows;i++){
			for(int j = 0;j<M.cols;j++){
				s = 0;
				for(int k = 0;k<this.cols;k++)
					s = s + this.data[i].data[k]*M.data[k].data[j];
				out.data[i].data[j] = s; 
			}
		}
		return out;
	}
	public eMatrix transpose(){
		eMatrix out = new eMatrix(this.cols,this.rows);
		for(int i = 0;i<this.cols;i++){
			for(int j = 0;j<this.rows;j++)
				out.data[i].data[j] = this.data[j].data[i];
		}
		return out;
	}
	//Saving and writing
	public void load(String dir){
		try{
			StringTokenizer st;
			File p = new File(dir);
			Scanner scan = new Scanner(p);
			int i=0,j;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				st = new StringTokenizer(line);
				j = 0;
				while(st.hasMoreTokens()){
					this.data[i].data[j] = Double.parseDouble(st.nextToken());
					j++;
				}
				i++;
			}
			scan.close();
		}
		catch(Exception e){}
	}
	public void save(String dir){
		try{
			File p = new File(dir);
			FileWriter writer = new FileWriter(p);
			for(int i = 0;i<this.rows;i++){
				for(int j = 0;j<this.cols;j++)
					writer.write(this.data[i].data[j] + " ");
				writer.write("\n");
			}
			writer.close();
		}
		catch(Exception e){}
	}
	public eMatrix[] LU(){
		eMatrix L,U;
		eMatrix[] out = new eMatrix[2];
		int i,j,k;
		double s;
		L = new eMatrix(this.rows,this.cols);
		U = new eMatrix(this.rows,this.cols);
		L.eye();
		for(i = 0;i<this.rows;i++){
			for(j = i;j<this.cols;j++){
				s = 0;
				for(k = 0;k<i;k++)
					s = s + L.data[i].data[k]*U.data[k].data[j];
				U.data[i].data[j] = this.data[i].data[j] - s;
			}
			for(j = i+1;j<this.cols;j++){
				s = 0;
				for(k = 0;k<i;k++)
					s = s + L.data[j].data[k]*U.data[k].data[i];
				L.data[j].data[i] = (this.data[j].data[i] - s)/U.data[i].data[i];
			}
		}
		out[0] = L;
		out[1] = U;
		return out;
	}
	public eMatrix[] QR(){
		eMatrix Q,R;
		eMatrix[] out = new eMatrix[2];
		eVector aux;
		eMatrix temp;
		Q = new eMatrix(this.rows,this.cols);
		R = new eMatrix(this.rows,this.cols);
		aux = new eVector(this.rows);
		Q.zeros();
		R.zeros();
		temp = this.transpose();
		for(int i = 0;i<this.rows;i++){
			aux.zeros();
			for(int j = 0;j<i;j++){
				aux = aux.add(temp.data[i].proj(Q.data[j]));
				R.data[i].data[j] = temp.data[i].dot(Q.data[j]);
			}
			Q.data[i] = temp.data[i].diff(aux);
			R.data[i].data[i] = Q.data[i].norm();
			Q.data[i].normalize();
		}
		out[0] = Q.transpose();
		out[1] = R.transpose();
		return out;
	}
	public double det(){
		eMatrix[] lu;
		double out;
		lu = this.LU();
		out = 1;
		for(int i = 0;i<this.rows;i++)
			out = out*lu[1].data[i].data[i];
		return out;
	}
	public eMatrix inv(){
		eMatrix[] lu;
		eMatrix A;
		eVector z;
		int i,j,k;
		double s;
		lu = this.LU();
		A = new eMatrix(this.rows,this.cols);
		z = new eVector(this.rows);
		for(j = 0;j<this.cols;j++){
			for(i=0;i<this.rows;i++){
				s = 0;
				for(k=0;k<i;k++)
					s = s + z.data[k]*lu[0].data[i].data[k];
				if(i!=j)
					z.data[i] = -s;
				else
					z.data[i] = 1 -s;
			}
			for(i=this.rows-1;i>=0;i--){
				s = 0;
				for(k=i+1;k<this.rows;k++)
					s = s + lu[1].data[i].data[k]*A.data[k].data[j];
				A.data[i].data[j] = (z.data[i] - s)/lu[1].data[i].data[i];
			}
		}
		return A;
	}
	public eMatrix[] eig(int N){
		eMatrix lambda, eigenv;
		eMatrix[] out;
		eMatrix[] qr;
		out = new eMatrix[2];
		eigenv = new eMatrix(this.rows,this.cols);
		eigenv.eye();
		lambda = this.copy();
		for(int i = 0;i<N;i++){
			qr = lambda.QR();
			eigenv = eigenv.dot(qr[0]);
			lambda = qr[1].dot(qr[0]);
		}
		out[0] = lambda;
		out[1] = eigenv;
		return out;
	}
}