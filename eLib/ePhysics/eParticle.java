package eLib.ePhysics;

import eLib.eMath.*;

public class eParticle{
	public eVector pos;
	public eVector vel;
	public double mass;

	public eParticle(double m){
		this.pos = new eVector(2);
		this.vel = new eVector(2);
		this.mass = m;
	}
	public eParticle(eVector pos,eVector vel,double m){
		this.pos = pos;
		this.vel = vel;
		this.mass = m;
	}
	public move(double dt){
		this.pos = this.pos.add(this.vel.times(dt));
	}
	public applyForce(eVector F,double dt){
		eVector a;
		a = F.times(1/this.mass);
		this.vel = this.vel.add(a.times(dt));
	}
}