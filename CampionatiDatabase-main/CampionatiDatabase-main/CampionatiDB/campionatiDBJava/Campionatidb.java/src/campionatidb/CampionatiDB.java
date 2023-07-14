package campionatidb;

import java.io.BufferedReader;
import java.sql.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class CampionatiDB {
	
	public CampionatiDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.printf("ClassNotFoundException:"+ e.getMessage());
		}
	}


	public void insertCampionato(String nome, String nazione) {    //INIZIO OPERAZIONE 1
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();

			st = con.createStatement();
			int result=st.executeUpdate("INSERT INTO campionato VALUES ('"+nome+"','"+nazione+"')");
			if (result> 0) {
				System.out.println("Inserimento effettuato");
				con.commit();
			} else {
				System.err.println("impossibile inserire il/i record");
			}
		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}//FINE OPERAZIONE 1
	
	
	
	
	
	
	public void retrieveSquadre(String nome) { //INIZIO OPERAZIONE 2
		Connection con = null;
		Statement st = null;
		ResultSet rs=null;
		try {
		con=DBConnectionPool.getConnection();
		st = con.createStatement();
		String sql = "SELECT nome FROM SQUADRA WHERE nomeCampionato=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1,nome);
		rs=ps.executeQuery();
		while(rs.next()) {
			String n=rs.getString(1);
			System.out.println(n);
			}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}//FINE OPERAZIONE 2
	
	
	
	
	
	public void insertTesserati(String idTesserati) throws IOException, SQLException { // OPERAZIONE 3
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {				
			con = DBConnectionPool.getConnection();
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			String nome,cognome,scadenzaContratto,nomeSquadra;
			int eta,stipendio;
			System.out.println("nome");
			nome=br.readLine();
			System.out.println("cognome");			
			cognome=br.readLine();
			System.out.println("scadenzaContratto (AAAA-MM-GG)");
			scadenzaContratto=br.readLine();
			System.out.println("nomeSquadra");
			nomeSquadra=br.readLine();
			System.out.println("et�");
			eta=Integer.parseInt(br.readLine());
			System.out.println("stipendio");
			stipendio=Integer.parseInt(br.readLine());
			st = con.createStatement();
			int result=st.executeUpdate("INSERT INTO tesserati VALUES ('"+idTesserati+"','"+nome+"','"+cognome+"','"+scadenzaContratto+"',"+eta+","+stipendio+",'"+nomeSquadra+"')");
			if (result>0) {
				con.commit();
			} else {
				System.err.println("impossibile inserire il/i record");
			}
		
			} catch (SQLException s) {
				throw(s);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (st != null)
						st.close();
				DBConnectionPool.releaseConnection(con);
				} catch (SQLException s) {
					throw(s);
				}
			}
	}//OPERAZIONE 3
	
	
	public void insertGiocatore(String idTesserati) { // INIZIO OPERAZIONE 3
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {				
			insertTesserati(idTesserati);
			con = DBConnectionPool.getConnection();
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			String ruolo;
			int golFatti,golSubiti,assist,partiteGiocate,trofei,cartelliniGialli,cartelliniRossi;
			golSubiti=0;
			System.out.println("ruolo:");			
			ruolo=br.readLine();
			System.out.println("gol fatti:");
			golFatti=Integer.parseInt(br.readLine());
			if(ruolo.contains("Portiere"))
				{
				System.out.println("gol subiti:");
				golSubiti=Integer.parseInt(br.readLine());
				}
			System.out.println("assist:");
			assist=Integer.parseInt(br.readLine());
			System.out.println("partite giocate:");
			partiteGiocate=Integer.parseInt(br.readLine());
			System.out.println("trofei:");
			trofei=Integer.parseInt(br.readLine());
			System.out.println("cartellini gialli:");
			cartelliniGialli=Integer.parseInt(br.readLine());
			System.out.println("cartellini rossi:");
			cartelliniRossi=Integer.parseInt(br.readLine());
			st = con.createStatement();
			int result;
			if(ruolo.contains("Portiere"))
				result=st.executeUpdate("INSERT INTO giocatore(idTesserati,ruolo,golFatti,golSubiti,assist,partiteGiocate,trofei,cartelliniGialli,cartelliniRossi) VALUES ('"+idTesserati+"','"+ruolo+"',"+golFatti+","+golSubiti+","+assist+","+partiteGiocate+","+trofei+","+cartelliniGialli+","+cartelliniRossi+")");
			else
				result=st.executeUpdate("INSERT INTO giocatore(idTesserati,ruolo,golFatti,golSubiti,assist,partiteGiocate,trofei,cartelliniGialli,cartelliniRossi) VALUES ('"+idTesserati+"','"+ruolo+"',"+golFatti+",NULL,"+assist+","+partiteGiocate+","+trofei+","+cartelliniGialli+","+cartelliniRossi+")");
			if (result>0) {
				System.out.println("Inserimento effettuato");
				con.commit();
			} else {
				System.err.println("impossibile inserire il/i record");
			}
		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} catch (IOException e) {
			System.err.println("Errore:"+e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Errore:"+e.getMessage());
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}//FINE OPERAZIONE 3
	
	
	
	public void retrieveTesserati(String nomeSquadra) { //INIZIO OPERAZIONE 4
		Connection con = null;
		Statement st = null;
		ResultSet rs=null;
		try {
		con=DBConnectionPool.getConnection();
		st = con.createStatement();
		String sql = "SELECT * FROM Tesserati WHERE nomeSquadra=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1,nomeSquadra);
		rs=ps.executeQuery();
		while(rs.next()) {
			String idTesserati=rs.getString(1);
			String nome=rs.getString(2);
			String cognome=rs.getString(3);
			String scadenzaContratto=rs.getString(4);
			int eta,stipendio;
			eta=rs.getInt(5);
			stipendio=rs.getInt(6);
			String snome=rs.getString(7);
			System.out.printf("%s	%s	%s	%s	%d	%d	%s\n",idTesserati,nome,cognome,scadenzaContratto,eta,stipendio,snome);
			}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}//FINE OPERAZIONE 4
	
	
	
	
	
	
	
	
	
	boolean verificaGol(String idTesserati) {  //INIZIO OPERAZIONE 5
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();
			String sql = "SELECT golFatti FROM Giocatore WHERE idTesserati=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,idTesserati);
			rs=ps.executeQuery();
			rs.next();
			int golFatti=rs.getInt(1);
			System.out.println(golFatti);
			if(golFatti>=100)
				return true;
			else
				return false;
			}
			catch(SQLException s) {
				System.err.println(s.getMessage());
				}finally 
				{	
					try
					{
						if(rs!=null)
							rs.close();
						if(st!=null)
							st.close();
						DBConnectionPool.releaseConnection(con);
					}
					catch(SQLException e) {
						System.err.println(e.getMessage());
					}
				}
		return false;
		
	} // FINE OPERAZIONE 5
	
	void stampaNPartite(String nomeSquadra) { 	//INIZIO OPERAZIONE 6
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT classifica.partitegiocate \r\n" + 
					"FROM classifica\r\n" + 
					"WHERE  classifica.nomeSquadra=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeSquadra);
			rs=ps.executeQuery();
			while(rs.next()) {
				int n=rs.getInt(1);
				System.out.println("numero partite giocate:"+n);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
	}//FINE OPERAZIONE 6
	
	
	public void insertPartita(String nomeCampionato) { //INIZIO OPERAZIONE 7
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {					
			con = DBConnectionPool.getConnection();
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			String codicePartita,data,ora,squadraCasa,squadraTrasferta;
			int giornata,golCasa,golTrasferta;		
			String sql="SELECT max(codicePartita) FROM	partita WHERE partita.nomeCampionato=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			rs=ps.executeQuery();
			rs.next();
			codicePartita=rs.getString(1);
			System.out.println("codicePartita	(Ultimo inserito= "+codicePartita+"):");			
			codicePartita=br.readLine();
			System.out.println("giornata:");
			giornata=Integer.parseInt(br.readLine());
			System.out.println("Squadra Casa:");
			squadraCasa=br.readLine();
			System.out.println("Squadra Trasferta:");
			squadraTrasferta=br.readLine();
			System.out.println("Gol casa:");
			golCasa=Integer.parseInt(br.readLine());
			System.out.println("Gol trasferta:");
			golTrasferta=Integer.parseInt(br.readLine());
			System.out.println("data(AAAA-MM-GG):");
			data=br.readLine();
			System.out.println("ora(HH:MM):");
			ora=br.readLine();
			st = con.createStatement();
			int result;
				result=st.executeUpdate("INSERT INTO partita(codicePartita,giornata,squadraCasa,squadraTrasferta,golCasa,golTrasferta,data,ora,nomeCampionato) VALUES ('"+codicePartita+"',"+giornata+",'"+squadraCasa+"','"+squadraTrasferta+"',"+golCasa+","+golTrasferta+",'"+data+"','"+ora+"','"+nomeCampionato+"')");
			if (result>0) {
				System.out.println("Inserimento effettuato");
				con.commit();
			} else {
				System.err.println("impossibile inserire il/i record");
			}
		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} catch (IOException e) {
			System.err.println("Errore:"+e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Errore:"+e.getMessage());
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}// FINE OPERAZIONE 7
	
	
	
	void etaMediamax(String nomeCampionato)  //INIZIO OPERAZIONE 8
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT tesserati.nomeSquadra, avg(tesserati.et�)as et�mediamax\r\n" + 
					"FROM tesserati,giocatore,squadra \r\n" + 
					"WHERE tesserati.idTesserati=giocatore.idTesserati AND squadra.nome=tesserati.nomeSquadra AND squadra.nomeCampionato=? \r\n" + 
					"GROUP BY tesserati.nomeSquadra	\r\n" + 
					"HAVING et�mediamax>=ALL(SELECT avg(tesserati.et�)\r\n" + 
					"					FROM tesserati,giocatore,squadra\r\n" + 
					"					WHERE tesserati.idTesserati=giocatore.idTesserati AND squadra.nome=tesserati.nomeSquadra  AND squadra.nomeCampionato=?\r\n" + 
					"					GROUP BY tesserati.nomeSquadra)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			ps.setString(2,nomeCampionato);
			rs=ps.executeQuery();
			while(rs.next()) {
				String nomeSquadra=rs.getString(1);
				float etamedia=rs.getFloat(2);
				System.out.printf("%s %.2f\n",nomeSquadra,etamedia);
				}}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
	}//FINE OPERAZIONE 8
	
	
	
	
	void retrievePresidente(String nomeCampionato) {  //INIZIO OPERAZIONE 9
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT tesserati.nome,tesserati.cognome,tesserati.nomeSquadra\r\n" + 
					"FROM tesserati,dirigenza\r\n" + 
					"WHERE  tesserati.idTesserati=dirigenza.idTesserati AND dirigenza.ruolo='Presidente'  AND tesserati.nomeSquadra IN (SELECT partita.squadraTrasferta\r\n" + 
					"																												   FROM  partita,squadra\r\n" + 
					"																												   WHERE partita.squadraTrasferta=squadra.nome AND squadra.nomeCampionato=? AND golTrasferta>golCasa\r\n" + 
					"																												   Group By partita.squadraTrasferta\r\n" + 
					"																												   HAVING COUNT(*)>=ALL(SELECT COUNT(*) \r\n" + 
					"																																		FROM  partita,squadra\r\n" + 
					"																																		WHERE partita.squadraTrasferta=squadra.nome AND squadra.nomeCampionato=? AND golTrasferta>golCasa\r\n" + 
					"																																		Group By partita.squadraTrasferta))\r\n" + 
					"";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			ps.setString(2,nomeCampionato);
			rs=ps.executeQuery();
			while(rs.next()) {
				String nome=rs.getString(1);
				String cognome=rs.getString(2);
				String nomeSquadra=rs.getString(3);
				System.out.printf("%s %s %s\n",nome,cognome,nomeSquadra);
				}}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		
		
	}//FINE OPERAZIONE 9
	
	
	void retrieveGiocatori(String nomeSquadra) { //INIZIO OPERAZIONE 10
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT tesserati.nome,tesserati.cognome,tesserati.stipendio\r\n" + 
					"FROM tesserati,giocatore\r\n" + 
					"WHERE tesserati.nomeSquadra=? AND tesserati.idTesserati=giocatore.idTesserati AND tesserati.stipendio>(SELECT tesserati.stipendio\r\n" + 
					"																												FROM tesserati,stafftecnico\r\n" + 
					"																										WHERE tesserati.nomeSquadra=? AND tesserati.idTesserati=staffTecnico.idTesserati AND staffTecnico.ruolo='Allenatore')";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeSquadra);
			ps.setString(2,nomeSquadra);
			rs=ps.executeQuery();
			while(rs.next()) {
				String nome=rs.getString(1);
				String cognome=rs.getString(2);
				int stipendio=rs.getInt(3);
				System.out.printf("%s %s %d�\n",nome,cognome,stipendio);
				}}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		
		
	}// FINE OPERAZIONE 10
	
	
	void cercaPartita(String nomeSquadra1,String nomeSquadra2) { //INIZIO OPERAZIONE 11
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT partita.*  \r\n" + 
					"FROM partita\r\n" + 
					"WHERE  (partita.squadraCasa=? OR partita.squadraTrasferta=?) AND (partita.squadraCasa=? OR partita.squadraTrasferta=?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeSquadra1);
			ps.setString(2,nomeSquadra1);
			ps.setString(3,nomeSquadra2);
			ps.setString(4,nomeSquadra2);
			rs=ps.executeQuery();
			while(rs.next()) {
				String idPartita=rs.getString(1);
				int giornata,golCasa,golTrasferta;
				giornata=rs.getInt(2);
				golCasa=rs.getInt(3);
				golTrasferta=rs.getInt(4);
				String data=rs.getString(5);
				String ora=rs.getString(6);
				String squadraCasa=rs.getString(7);
				String squadraTrasferta=rs.getString(8);
				String nomeCampionato=rs.getString(9);
				System.out.printf("Giornata:%d	%s	%d-%d	%s		%s	%s	%s		%s\n",giornata,squadraCasa,golCasa,golTrasferta,squadraTrasferta,data,ora,idPartita,nomeCampionato);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
	}//FINE OPERAZIONE 11

	void differenzaReti(String nomeCampionato) {  //INIZIO OPERAZIONE 12
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT classifica.nomeSquadra,classifica.golFatti,classifica.golSubiti,(classifica.golFatti-classifica.golSubiti) AS DifferenzaReti\r\n" + 
					"FROM classifica\r\n" + 
					"where classifica.nomeCampionato=? AND (classifica.golFatti-classifica.golSubiti) IN(SELECT max(classifica.golFatti-classifica.golSubiti) \r\n" + 
					"														FROM classifica\r\n" + 
					"                                                         WHERE classifica.nomeCampionato=? )";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			ps.setString(2,nomeCampionato);
			rs=ps.executeQuery();
			while(rs.next()) {
				String nomeSquadra=rs.getString(1);
				int golCasa,golTrasferta,differenzaReti;
				golCasa=rs.getInt(2);
				golTrasferta=rs.getInt(3);
				differenzaReti=rs.getInt(4);
				System.out.printf("Nome:%s			  Gol Fatti:%d	 		Gol Subiti:%d	   	Differenza Reti:%d\n",nomeSquadra,golCasa,golTrasferta,differenzaReti);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		
	}//FINE OPERAZIONE 12
	
	
	
	
	
	void golTotalipartita(String nomeCampionato) {//INIZIO OPERAZIONE 13
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT p.*,max(p.golCasa+p.golTrasferta) as golTotali\r\n" + 
					"FROM partita as p\r\n" + 
					"WHERE p.nomeCampionato=? AND ((p.golCasa+p.golTrasferta) IN (SELECT max((p1.golCasa+p1.golTrasferta))\r\n" + 
					"																		FROM partita AS p1\r\n" + 
					"																		WHERE p1.nomeCampionato=? AND p1.giornata=p.giornata\r\n" + 
					"																	    GROUP BY p1.giornata)	)\r\n" + 
					"GROUP BY p.giornata";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			ps.setString(2,nomeCampionato);
			rs=ps.executeQuery();
			while(rs.next()) {
				String idPartita=rs.getString(1);
				int giornata=rs.getInt(2);
				int golCasa=rs.getInt(3);
				int golTrasferta=rs.getInt(4);
				String data=rs.getString(5);
				String ora=rs.getString(6);
				String squadraCasa=rs.getString(7);
				String squadraTrasferta=rs.getString(8);
				System.out.printf("Giornata:%d	%s	%d-%d	%s		%s	%s		%s\n",giornata,squadraCasa,golCasa,golTrasferta,squadraTrasferta,data,ora,idPartita);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		
	}//FINE OPERAZIONE 13

	void stampaPartite(String nomeSquadra) { 	//INIZIO OPERAZIONE 14
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT partita.*  \r\n" + 
					"FROM partita\r\n" + 
					"WHERE  (partita.squadraCasa=? OR partita.squadraTrasferta=?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeSquadra);
			ps.setString(2,nomeSquadra);
			rs=ps.executeQuery();
			while(rs.next()) {
				String idPartita=rs.getString(1);
				int giornata,golCasa,golTrasferta;
				giornata=rs.getInt(2);
				golCasa=rs.getInt(3);
				golTrasferta=rs.getInt(4);
				String data=rs.getString(5);
				String ora=rs.getString(6);
				String squadraCasa=rs.getString(7);
				String squadraTrasferta=rs.getString(8);
				String nomeCampionato=rs.getString(9);
				System.out.printf("Giornata:%d	%s		%d-%d	%s		%s		%s		%s		%s\n",giornata,squadraCasa,golCasa,golTrasferta,squadraTrasferta,data,ora,idPartita,nomeCampionato);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
	}//FINE OPERAZIONE 14
	
	
	
	public void minGolFatti(String nomeCampionato) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();
			String sql="SELECT squadra.nome, SUM(giocatore.golFatti) as GolTotaliGiocatori\r\n" + 
					"FROM squadra,tesserati,giocatore\r\n" + 
					"WHERE squadra.nomeCampionato=? AND squadra.nome=tesserati.nomeSquadra AND tesserati.idTesserati=giocatore.idTesserati \r\n" + 
					"GROUP BY squadra.nome\r\n" + 
					"HAVING SUM(giocatore.golFatti)>ANY (SELECT SUM(G.golFatti)\r\n" + 
					"							   FROM squadra AS S,tesserati AS T,giocatore AS G\r\n" + 
					"							   WHERE S.nomeCampionato=? AND S.nome=T.nomeSquadra AND T.idTesserati=G.idTesserati\r\n" + 
					"							  GROUP BY S.nome)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,nomeCampionato);
			ps.setString(2,nomeCampionato);
			rs=ps.executeQuery();
			while(rs.next()) {
				String squadra=rs.getString(1);
				int golTotali=rs.getInt(2);
				System.out.printf("Squadra:%s 		Gol fatti dai giocatori:%d\n",squadra,golTotali);
				}
		}
		catch(SQLException s) {
			System.err.println(s.getMessage());
			}finally 
			{	
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					DBConnectionPool.releaseConnection(con);
				}
				catch(SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		
	
	}

	
	
	
	
		private void ui() throws IOException {
			// inizio main
			InputStreamReader is;
			BufferedReader br;
			int i=0;
			String scelta;

			is = new InputStreamReader(System.in);
			br = new BufferedReader(is);

			while(i!=1000) {// inizio while
				System.out.println("Operazioni:");
				System.out.println("1, Aggiungi campionato");
				System.out.println("2, Mostra le squadre che partecipano ad un campionato");
				System.out.println("3, Inserisci giocatore");
				System.out.println("4, Stampa i tesserati di una squadra");
				System.out.println("5, Verificare se un giocatore ha fatto almeno 100 gol");
				System.out.println("6, Stampare il numero di partite giocate da una squadra");
				System.out.println("7, Aggiungere una nuova partita");
				System.out.println("8, Stampare l'et� media massima dei giocatori di una squadra partecipante ad un determinato campionato ");
				System.out.println("9, Mostra il presidente della squadra con pi� vittorie in trasferta  di un campionato ");
				System.out.println("10, Trova calciatori di una squadra che guadagnano pi� del proprio allenatore ");
				System.out.println("11, Mostra le partite disputate tra due squadre");
				System.out.println("12, Stampa la squadra con la migliore differenza reti di un campionato");
				System.out.println("13, Per ogni giornata  visualizza la partita, di un campionato a scelta, terminata con pi� gol");
				System.out.println("14, Stampare tutte le partite giocate da una squadra");
				System.out.println("15, Stampare tutte le squadre di un campionato, tranne quella i cui giocatori hanno segnato complessivamente meno gol");
				System.out.println("1000, Per uscire");
				System.out.println("Inserisci scelta: ");
				scelta = br.readLine();

				try {// inizio try-catch
					i = Integer.parseInt(scelta);
				} catch (NumberFormatException e) {
					i = 999;
				} // fine try-catch

				switch (i) {//inizio switch
				
				
				case 1: {
					String nome,nazione;
					System.out.println("Aggiungi Campionato");
					System.out.println("Inserisci nome:");
					nome=br.readLine();
					System.out.println("Inserisci nazione:");
					nazione=br.readLine();
					insertCampionato(nome,nazione);
					break;
				}
				
				
				case 2: {
					String nome;
					System.out.println("Mostra le squadre che partecipano ad un campionato");
					System.out.println("Inserisci nome campionato:");
					nome=br.readLine();
					retrieveSquadre(nome);
					break;
				}
				
				
				case 3: {
					String idTesserati;
					System.out.println("Inserisci giocatore:");
					System.out.println("Inserisci idTesserati:");
					idTesserati=br.readLine();
					insertGiocatore(idTesserati);
					break;
				}
				
				
				
				case 4: {
						String nomeSquadra;
						System.out.println("Inserisci nome squadra:");
						nomeSquadra=br.readLine();
						retrieveTesserati(nomeSquadra);
						break;
				}
				
				
				case 5:{
						String idTesserati;
						System.out.println("Inserisci idTesserati giocatore:");
						idTesserati=br.readLine();
						if(verificaGol(idTesserati))
							System.out.println(idTesserati+" ha fatto almeno 100 gol");
						else
							System.out.println(idTesserati+" non ha fatto 100 gol");

						break;
				}
				
				case 6:{
					String nomeSquadra;
					System.out.println("Inserisci nome della squadra:");
					nomeSquadra=br.readLine();
					stampaNPartite(nomeSquadra);
					break;
				}
				
				case 7:{
					String nomeCampionato;
					System.out.println("Inserisci nome del campionato:");
					nomeCampionato=br.readLine();
					insertPartita(nomeCampionato);
					break;
					}
			
				
				case 8:{
					String nomeCampionato;
					System.out.println("Inserisci nome del campionato:");
					nomeCampionato=br.readLine();
					etaMediamax(nomeCampionato);
					break;
					}
				
				
				case 9:{
					String nomeCampionato;
					System.out.println("Inserisci nome del campionato:");
					nomeCampionato=br.readLine();
					retrievePresidente(nomeCampionato);
					
					break;
					}
				
				
				case 10:{
					String nomeSquadra;
					System.out.println("Inserisci nome della squadra:");
					nomeSquadra=br.readLine();
					retrieveGiocatori(nomeSquadra);
					break;
					}
				
				case 11:{
					String nomeSquadra1,nomeSquadra2;
					System.out.println("Inserisci nome della prima squadra:");
					nomeSquadra1=br.readLine();
					System.out.println("Inserisci nome della seconda squadra:");
					nomeSquadra2=br.readLine();
					cercaPartita(nomeSquadra1,nomeSquadra2);
					break;
					}
				
				case 12:{String nomeCampionato;
						System.out.println("Inserisci nome del campionato:");
						nomeCampionato=br.readLine();
						differenzaReti(nomeCampionato);
						break;
					
					}
				

				case 13:{String nomeCampionato;
						System.out.println("Inserisci nome del campionato:");
						nomeCampionato=br.readLine();
						golTotalipartita(nomeCampionato);
						break;
					
					}
				case 14:{
					String nomeSquadra;
					System.out.println("Inserisci nome della squadra:");
					nomeSquadra=br.readLine();
					stampaPartite(nomeSquadra);
					break;
				}
				
				case 15:{String nomeCampionato;
				System.out.println("Inserisci nome del campionato:");
				nomeCampionato=br.readLine();
				minGolFatti(nomeCampionato);
				break;
			
			}
				case 1000: {
					System.out.println("Uscita");
					break;
				}
				default: {
					System.out.println("Scelta non presente");
					break;
				}
				} // fine switch
			} // fine while

	
}
	
		public static void main (String [] args) throws Exception{
			CampionatiDB cdb= new CampionatiDB();
			cdb.ui();
		}
		
}
	

