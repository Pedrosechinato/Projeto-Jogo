package Jogo;


public class Lista <X> extends Thread
{
 private Object[] item;
 private int      inicio =  0;
 private int      ultimo    = -1;
 private int      qtd    =  -1;


public Lista() {
	
	this.item = new Object [99999];
	
}
synchronized public Object getItem(int i)throws Exception{	
		if (this.qtd==-1)
			throw new Exception ("Nada guardado");
		
	return item[i];
}
public int getQtd() {	
	return qtd;
}
synchronized public int getInicio() {	
	return inicio;
}

 synchronized public void guarde (Object p) throws Exception
 {
    if (p==null)
        throw new Exception ("Falta o que guardar");
     
     if (this.qtd==this.item.length)
         throw new Exception ("Nao cabe mais nada");
     
     this.ultimo++;
     if (this.ultimo==this.item.length)
         this.ultimo = 0;
    item[ultimo] = p;
     this.qtd++;
 }
 public boolean vazia()
 {
     if (this.qtd==-1)
         return true;
     else
         return false;
 }

 synchronized public void jogueForaUmItem () throws Exception
 {
     if (this.qtd==-1)
         throw new Exception ("Nada guardado");

     this.item[this.inicio] = null;
     this.inicio++;
     if (this.inicio==this.item.length)
         this.inicio = 0;
     this.qtd--;
 }


 public boolean equals (Object obj)
 {
     if (this==obj)
         return true;

     if (obj==null)
         return false;

     if (this.getClass()!=obj.getClass())
         return false;

     @SuppressWarnings("unchecked")
	Lista<Partida> fil = (Lista<Partida>)obj;

     if (this.qtd!=fil.qtd)
         return false;

     int iThis = this.inicio,
         iFil  = fil .inicio;

     for (int i=0; i<this.qtd; i++)
     {
         if (!this.item[iThis].equals(fil.item[iFil]))
             return false;

         iThis++;
         if (iThis==this.item.length)
             iThis=0;

         iFil++;
         if (iFil==fil.item.length)
             iFil=0;
     }

     return true;
 }

 public String toString ()
 {
     String ret="";

     int iThis = this.inicio;

     for (int i=0; i<this.qtd; i++)
     {
         ret += this.item [iThis];

         if (iThis!=this.ultimo)
             ret += ",";

         iThis++;
         if (iThis==this.item.length)
             iThis=0;
     }

     return ret;
 }

 public int hashCode ()
 {
     int ret=666; //so nao vale zero

     ret = 7*ret + new Integer(this.inicio).hashCode ();
     ret = 7*ret + new Integer(this.ultimo   ).hashCode ();
     ret = 7*ret + new Integer(this.qtd   ).hashCode ();

     int iThis = this.inicio;

     for (int i=0; i<this.qtd; i++)
     {
         ret = 7*ret + this.item[iThis].hashCode();

         iThis++;
         if (iThis==this.item.length)
             iThis=0;
     }

     return ret;
 }
}