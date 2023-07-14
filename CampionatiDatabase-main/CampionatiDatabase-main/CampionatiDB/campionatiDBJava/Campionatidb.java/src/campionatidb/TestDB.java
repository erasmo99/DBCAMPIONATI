package campionatidb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
	public TestDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.printf("ClassNotFoundException:"+ e.getMessage());
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
				System.out.println("1, Cancella i tesserati con il contratto scaduto");
				System.out.println("2, Stampa le classifiche di tutti i campionati");
				System.out.println("3, Stampa tutte le squadre in ordine alfabetico");
				System.out.println("4, Mostra i tesserati i cui cognomi iniziano con la lettera 'e'");
				System.out.println("5, Visualizza tutti i medici sociali del campionato italiano");
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
					Connection con = null;
					Statement st = null;
					ResultSet rs = null;
					try {
						con = DBConnectionPool.getConnection();

						st = con.createStatement();
						int result=0;
						String sql="DELETE stafftecnico.*,tesserati.*\r\n" + 
								"FROM tesserati,stafftecnico\r\n" + 
								"WHERE tesserati.idTesserati=stafftecnico.idTesserati AND (SELECT datediff(tesserati.scadenzaContratto,current_date()))<0";
						result+=st.executeUpdate(sql);
						sql="DELETE staffmedico.*,tesserati.*\r\n" + 
								"FROM tesserati,staffmedico\r\n" + 
								"WHERE tesserati.idTesserati=staffmedico.idTesserati AND (SELECT datediff(tesserati.scadenzaContratto,current_date()))<0";
						result+=st.executeUpdate(sql);
						sql="DELETE giocatore.*,tesserati.*\r\n" + 
								"FROM giocatore,tesserati\r\n" + 
								"WHERE tesserati.idTesserati=giocatore.idTesserati AND (SELECT datediff(tesserati.scadenzaContratto,current_date()))<0";
						result+=st.executeUpdate(sql);
						sql="DELETE dirigenza.*,tesserati.*\r\n" + 
								"FROM dirigenza,tesserati\r\n" + 
								"WHERE tesserati.idTesserati=dirigenza.idTesserati AND (SELECT datediff(tesserati.scadenzaContratto,current_date()))<0";
						result+=st.executeUpdate(sql);
						if (result>0) {
							System.out.println("Cancellazione effettuata");
							con.commit();
						} else {
							System.err.println("Nessuna cancellazione effettuata");
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
							}
							catch (SQLException s) {
							System.err.println(s.getMessage());
							}
						}
					break;
				}
				
				
				case 2: {
					Connection con = null;
					Statement st = null;
					ResultSet rs = null;
					try {
						con = DBConnectionPool.getConnection();

						st = con.createStatement();
						
						String sql="SELECT classifica.*\r\n" + 
								"FROM classifica \r\n" + 
								"ORDER BY classifica.nomeCampionato,classifica.punti DESC";
						rs=st.executeQuery(sql);
						System.out.println("Campionato	Stagione	Squadra		PG	V	P	S	GF	GS");
						while(rs.next()) {
							String nomeSquadra,nomeCampionato;
							int stagione,punti,partitegiocate,golFatti,golSubiti,vittorie,pareggi,sconfitte;
							stagione=rs.getInt(1);
							punti=rs.getInt(2);
							partitegiocate=rs.getInt(3);
							golFatti=rs.getInt(4);
							golSubiti=rs.getInt(5);
							vittorie=rs.getInt(6);
							pareggi=rs.getInt(7);
							sconfitte=rs.getInt(8);
							nomeSquadra=rs.getString(9);
							nomeCampionato=rs.getString(10);
							System.out.printf("%s	%d		%s		%d	%d	%d	%d	%d	%d\n",nomeCampionato,stagione,nomeSquadra,partitegiocate,punti,vittorie,pareggi,sconfitte,golFatti,golSubiti);
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
							}
							catch (SQLException s) {
							System.err.println(s.getMessage());
							}
					}
					break;
				}
				
				
				case 3: {Connection con = null;
				Statement st = null;
				ResultSet rs = null;
				try {
					con = DBConnectionPool.getConnection();

					st = con.createStatement();
					
					String sql="SELECT squadra.*\r\n" + 
							"FROM squadra \r\n" + 
							"ORDER BY squadra.nome ASC";
					rs=st.executeQuery(sql);
					while(rs.next()) {
						String nomeSquadra,nomeCampionato,citta,stadio;
						int trofei;
						nomeSquadra=rs.getString(1);
						stadio=rs.getString(2);
						citta=rs.getString(3);
						trofei=rs.getInt(4);
						nomeCampionato=rs.getString(5);
						System.out.printf("Squadra:%s	Stadio:%s	Citt�:%s	Trofei:%d	Campionato:%s\n\n",nomeSquadra,stadio,citta,trofei,nomeCampionato);
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
						}
						catch (SQLException s) {
						System.err.println(s.getMessage());
						}
				}
					break;
				}
				
				
				
				case 4: {
					Connection con = null;
					Statement st = null;
					ResultSet rs = null;
					try {
						con = DBConnectionPool.getConnection();

						st = con.createStatement();
						
						String sql="SELECT tesserati.*\r\n" + 
								"FROM tesserati\r\n" + 
								"WHERE tesserati.cognome LIKE('c%')";
						rs=st.executeQuery(sql);
						while(rs.next()) {
							String idTesserati,nome,cognome,scadenzaContratto,nomeSquadra;
							int eta,stipendio;
							idTesserati=rs.getString(1);
							nome=rs.getString(2);
							cognome=rs.getString(3);
							scadenzaContratto=rs.getString(4);
							eta=rs.getInt(5);
							stipendio=rs.getInt(6);
							nomeSquadra=rs.getString(7);
;
							System.out.printf("Idtesserati:%s	Nome:%s		Cognome:%s		ScadenzaContratto:%s	Et�:%d	Stipendio:%d�	Squadra:%s\n",idTesserati,nome,cognome,scadenzaContratto,eta,stipendio,nomeSquadra);
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
							}
							catch (SQLException s) {
							System.err.println(s.getMessage());
							}
					}
						break;
				}
				
				
				case 5:{Connection con = null;
				Statement st = null;
				ResultSet rs = null;
				try {
					con = DBConnectionPool.getConnection();

					st = con.createStatement();
					
					String sql="SELECT tesserati.*,staffmedico.*\r\n" + 
							"FROM tesserati,staffmedico,squadra,campionato\r\n" + 
							"WHERE	campionato.nazione='Italia'AND campionato.nome=squadra.nomeCampionato AND squadra.nome=tesserati.nomeSquadra AND tesserati.idTesserati=staffmedico.idTesserati AND staffmedico.specializzazione='Responsabile Sanitario'";
					rs=st.executeQuery(sql);
					while(rs.next()) {
						String idTesserati,nome,cognome,scadenzaContratto,nomeSquadra;
						int eta,stipendio;
						idTesserati=rs.getString(1);
						nome=rs.getString(2);
						cognome=rs.getString(3);
						scadenzaContratto=rs.getString(4);
						eta=rs.getInt(5);
						stipendio=rs.getInt(6);
						nomeSquadra=rs.getString(7);
						System.out.printf("Idtesserati:%s	Nome:%s		Cognome:%s		ScadenzaContratto:%s	Eta:%d	Stipendio:%d�	Squadra:%s\n",idTesserati,nome,cognome,scadenzaContratto,eta,stipendio,nomeSquadra);
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
						}
						catch (SQLException s) {
						System.err.println(s.getMessage());
						}
				}

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
				}
			}// fine switch
		} // fine while	
	
		public static void main (String [] args) throws Exception{
			TestDB tdb= new TestDB();
			tdb.ui();
		}
		
	}

