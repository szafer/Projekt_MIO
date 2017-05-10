package projekt.funkcje;
//f(x,y) = -20 * exp(-0.2*sqrt(0.5*(x^2 + y^2))) -
//- exp(0.5*cos(2*pi*x) + cos(2*pi*y)))
//+ 20 + e


//-->[x,y]=meshgrid(-4:.10:4,-4:.10:4);
//-->z=-20 * exp(-0.2*sqrt(0.5 * (x.^2 + y.^2)))
//     - exp(0.5 * (cos(2*%pi*x) + cos(2*%pi*y)))
//     + 20 + %e;
//-->g=scf();
//-->g.color_map=jetcolormap(64);
//-->surf(x,y,z);
public class F_Ackley {

}
//Graphing the 3D Double-Dip Function
//-->[x,y]=meshgrid(-2:.15:2,-2:.15:2);
//-->z=x .*exp(-(x.^2+y.^2));
//-->g=scf();
//-->g.color_map=jetcolormap(64);
//-->surf(x,y,z);
//One such function I sometimes use is f(x,y) = x * exp( -(x^2 + y^2) ). 