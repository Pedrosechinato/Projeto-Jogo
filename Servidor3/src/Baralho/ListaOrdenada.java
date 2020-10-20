/*package Baralho;

public class ListaOrdenada <X extends Comparable<X>> //implements Cloneable
{
	private class Lista //implements Cloneable
	{
		private X  info;
		private Lista prox;
		
		public void setInfo (X i)
		{
			this.info = i;
		}
		
		public void setProx (Lista p)
		{
			this.prox = p;
		}
		
		public X getInfo ()
		{
			return this.info;
		}
		
		public Lista getProx ()
		{
			return this.prox;
		}
		
		public Lista (X i, Lista p)
		{
			this.setInfo (i);
			this.setProx (p);
		}
		
		public Lista (X i)
		{
			this (i,null);
		}
	}
	
	private Lista prim=null;

	public int size ()
	{
		int cont=0;
		
		Lista atual = this.prim;
		
		while (atual!=null)
		{
			cont++;
			atual=atual.getProx();
		}
		
		return cont;
	}
	
	public boolean tem (X i)
	{	
		Lista atual = this.prim;
		
		while (atual!=null)
		{
			if (i.equals(atual.getInfo()))
				return true;
			
			atual=atual.getProx();
		}
		
		return false;
	}
	
	public void removerDoInicio () throws Exception
	{
		if (this.prim==null)
			throw new Exception ("Lista vazia");
		
		this.prim = this.prim.getProx ();
	}
	
	public void removerDoFim () throws Exception
	{
		if (this.prim==null)
			throw new Exception ("Lista vazia");

		if (this.prim.getProx()==null)
			this.prim=null;
		else
		{	
    		Lista atual = this.prim;
		
	    	while (atual.getProx().getProx() != null)
		    	atual = atual.getProx();
			
			atual.setProx (null);
		}	
	}

	public void remover (X i) throws Exception
	{
		if (i==null)
			throw new Exception ("Informacao ausente");
        
		if (this.prim==null)
			throw new Exception ("Lista vazia");

		if (i.equals(this.prim.getInfo()))
            this.prim = this.prim.getProx();
		else
		{
			Lista anterior=this.prim,
			   atual=this.prim.getProx();
			   
			for(;;)
			{
				if (atual==null)
					throw new Exception ("Informacao inexistente");
				
				int comp=atual.getInfo().compareTo(i);
				
				if (comp>0)
					throw new Exception ("Informacao inexistente");
				
				if (comp==0)
				{
					anterior.setProx(atual.getProx());
					break;
				}
				
			  //if (comp<0)
			  //{
				    anterior=atual;
					atual   =atual.getProx();
			  //}
			}
		}
	}

	public void add(Carta carta) {
		
		
	}
}
*/