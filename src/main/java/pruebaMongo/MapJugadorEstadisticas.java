package pruebaMongo;

import java.util.ArrayList;

import org.bson.Document;

import Coleccion.Temporadas;
import Modelo.ClaseEquipo;
import Modelo.ClaseEstadisticaAvanzada;
import Modelo.ClaseEstadisticaNormal;
import Modelo.ClaseEstadisticaNormalTotales;
import Modelo.ClaseFullBoxscore;
import Modelo.ClaseJugador;
import Modelo.ClaseJugadorTiros;
import Modelo.ClasePartido;
import Modelo.ClaseTanteoCuartos;
import Modelo.ClaseTiros;

public class MapJugadorEstadisticas {

	
	public static ClasePartido rellenarJuegadorEstadisticas(Document partido, String id,String contrario) {
		
		ClasePartido match = new ClasePartido();

			match.setHora((String) partido.get("hora"));
			match.setDia((String) partido.get("dia"));
			match.setMes((String) partido.get("mes"));
			match.setYear((String) partido.get("year"));
			match.setPlayOff((boolean) partido.get("playOff"));
			System.out.println(match.getDia()+" / "+match.getMes()+" / "+match.getYear()+" Contrario: "+contrario);
			if("visitante".equals(contrario)) {
				match.setEquipoVisitante(rellenarEquipoJugadorEstadistica((Document)partido.get("equipoVisitante"),id)); //EquipoVisitante
			}else if("local".equals(contrario)) {
				match.setEquipoLocal(rellenarEquipoJugadorEstadistica((Document)partido.get("equipoLocal"),id));	//EquipoLocal
			}
		
		return match;
	}
	
	private static ClaseEquipo rellenarEquipoJugadorEstadistica(Document team,String id){
		ClaseEquipo equipo = new ClaseEquipo();
		
			equipo.setJugadores(devolverJugadores((ArrayList<Document>)team.get("jugadores"),id)); // Array Jugadores
			equipo.setNombre((String) team.get("nombre"));
			equipo.setTanteo((String) team.get("tanteo"));
			equipo.setTanteoCuartos(devolverCuartos((Document)team.get("tanteoCuartos"))); // Tanteo Cuartos
			equipo.setVictorias((String) team.get("victorias"));
		return equipo;
		
	}
	
	private static ArrayList<ClaseJugador> devolverJugadores(ArrayList<Document> lista,String id){
		ArrayList<ClaseJugador> listaJugadores = new ArrayList<ClaseJugador>();
			for(int i=0;i<lista.size();i++) {
				ClaseJugador jug = devolverJugador((Document)lista.get(i),id);
				if(jug!=null) {
					listaJugadores.add(jug);
				}
				
			}		
		return listaJugadores;
	}
	
	private static ClaseJugador devolverJugador(Document player,String id) {
		ClaseJugador jugador = new ClaseJugador();
		
		if(id.equals((String) player.get("id"))) {
			jugador.setBoxscore(devolverEstadisticaNormal((Document) player.get("boxscore"))); // BoxScore
			jugador.addCuartoJugador(devolverFullBox((Document)player.get("cuarto1"))); // Cuartos	
			jugador.addCuartoJugador(devolverFullBox((Document)player.get("cuarto2")));
			jugador.addCuartoJugador(devolverFullBox((Document)player.get("cuarto3")));
			jugador.addCuartoJugador(devolverFullBox((Document)player.get("cuarto4")));
			if(player.get("over1")!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get("over1")));}
			if(player.get("over2")!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get("over2")));}
			if(player.get("over3")!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get("over3")));}
			if(player.get("over4")!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get("over4")));}
			jugador.setEstadisticaAvanzada(devolverEstadisticaAvanzada((Document)player.get("estadisticaAvanzada"))); // EstadisticaAvanzada
			jugador.setInicio((Boolean) player.get("inicio"));
			jugador.setListaTiros(devolverCartaTiro((ArrayList<Document>)player.get("listaTiros"))); // ListaTiros
			jugador.setSegundos((Integer) player.get("segundos"));
			jugador.setTotalPartido(devolverFullBox((Document)player.get("totalPartido"))); // TotalPartido
			
			return jugador;
		}
		
		return null;
	}
	
	private static ClaseEstadisticaAvanzada devolverEstadisticaAvanzada(Document advan){
		ClaseEstadisticaAvanzada avanzadas = new ClaseEstadisticaAvanzada();
		
			avanzadas.setFANTASY((Double)revisarDatoIntegerDouble(advan.get("FANTASY")));
			avanzadas.setFTARate((Double)revisarDatoIntegerDouble(advan.get("FTARate")));
			avanzadas.setNETRTG((Double)revisarDatoIntegerDouble(advan.get("NETRTG")));
			avanzadas.setAssistPercentage((Double)revisarDatoIntegerDouble(advan.get("assistPercentage")));
			avanzadas.setBlockPercentage((Double)revisarDatoIntegerDouble(advan.get("blockPercentage")));
			avanzadas.setDefensiveRating((Double)revisarDatoIntegerDouble(advan.get("defensiveRating")));
			avanzadas.setDefensiveReboundPer((Double)revisarDatoIntegerDouble(advan.get("defensiveReboundPer")));
			avanzadas.setEffectiveGoalPer((Double)revisarDatoIntegerDouble(advan.get("effectiveGoalPer")));
			avanzadas.setFreeThrowRate((Double)revisarDatoIntegerDouble(advan.get("freeThrowRate")));
			avanzadas.setOffensiveRating((Double)revisarDatoIntegerDouble(advan.get("offensiveRating")));
			avanzadas.setOffensiveReboundPer((Double)revisarDatoIntegerDouble(advan.get("offensiveReboundPer")));
			avanzadas.setPerPTS1((Double)revisarDatoIntegerDouble(advan.get("perPTS1")));
			avanzadas.setPerPTS2((Double)revisarDatoIntegerDouble(advan.get("perPTS2")));
			avanzadas.setPerPTS2PT((Double)revisarDatoIntegerDouble(advan.get("perPTS2PT")));
			avanzadas.setPerPTS2PTM((Double)revisarDatoIntegerDouble(advan.get("perPTS2PTM")));
			avanzadas.setPerPTS3((Double)revisarDatoIntegerDouble(advan.get("perPTS3")));
			avanzadas.setPerPTS3PT((Double)revisarDatoIntegerDouble(advan.get("perPTS3PT")));
			avanzadas.setPerPTS3PTM((Double)revisarDatoIntegerDouble(advan.get("perPTS3PTM")));
			avanzadas.setStealPercentage((Double)revisarDatoIntegerDouble(advan.get("stealPercentage")));
			avanzadas.setThreePointRate((Double)revisarDatoIntegerDouble(advan.get("threePointRate")));
			avanzadas.setTotalReboundPer((Double)revisarDatoIntegerDouble(advan.get("totalReboundPer")));
			avanzadas.setTrueShootPer((Double)revisarDatoIntegerDouble(advan.get("trueShootPer")));
			avanzadas.setTurnoverPercentage((Double)revisarDatoIntegerDouble(advan.get("turnoverPercentage")));
			avanzadas.setUsagePercentage((Double)revisarDatoIntegerDouble(advan.get("usagePercentage")));
		
		return avanzadas;
	}
	
	private static ClaseEstadisticaNormal devolverEstadisticaNormal(Document nor) {
		ClaseEstadisticaNormal normal = new ClaseEstadisticaNormal();
		
			normal.setAsistencias((Integer) nor.get("asistencias"));
			normal.setCuarto((String) nor.get("cuarto"));
			normal.setFaltasPersonales((Integer) nor.get("faltasPersonales"));
			normal.setPerdidas((Integer) nor.get("perdidas"));
			normal.setPuntos((Integer) nor.get("puntos"));
			normal.setReboteDefensivo((Integer) nor.get("reboteDefensivo"));
			normal.setReboteOfensivo((Integer) nor.get("reboteOfensivo"));
			normal.setRobos((Integer) nor.get("robos"));
			normal.setTapones((Integer) nor.get("tapones"));
			normal.setTirosCampoIntentados((Integer) nor.get("tirosCampoIntentados"));
			normal.setTirosCampoMetidos((Integer) nor.get("tirosCampoMetidos"));
			normal.setTirosCampoPorcentaje((Double)revisarDatoIntegerDouble(nor.get("tirosCampoPorcentaje")));
			normal.setTirosLibresIntentados((Integer) nor.get("tirosLibresIntentados"));
			normal.setTirosLibresMetidos((Integer) nor.get("tirosLibresMetidos"));
			normal.setTirosLibresPorcentaje((Double)revisarDatoIntegerDouble(nor.get("tirosLibresPorcentaje")));
			normal.setTotalRebotes((Integer) nor.get("totalRebotes"));
			normal.setTriplesIntentados((Integer) nor.get("triplesIntentados"));
			normal.setTriplesMetidos((Integer) nor.get("triplesMetidos"));
			normal.setTriplesPorcentaje((Double)revisarDatoIntegerDouble(nor.get("triplesPorcentaje")));
		
		return normal;
	}

	private static ClaseTanteoCuartos devolverCuartos(Document cuartos) {
		ClaseTanteoCuartos tanteoCuartos = new ClaseTanteoCuartos();
		
			tanteoCuartos.insertarCuarto("primero",Integer.parseInt((String) cuartos.get("primero")));
			tanteoCuartos.insertarCuarto("segundo",Integer.parseInt((String) cuartos.get("segundo")));
			tanteoCuartos.insertarCuarto("tercero",Integer.parseInt((String) cuartos.get("tercero")));
			tanteoCuartos.insertarCuarto("cuarto",Integer.parseInt((String) cuartos.get("cuarto")));
			if(cuartos.get("OT1")!=null){tanteoCuartos.insertarCuarto("OT1",Integer.parseInt((String) cuartos.get("OT1")));}
			if(cuartos.get("OT2")!=null){tanteoCuartos.insertarCuarto("OT2",Integer.parseInt((String) cuartos.get("OT2")));}
			if(cuartos.get("OT3")!=null){tanteoCuartos.insertarCuarto("OT3",Integer.parseInt((String) cuartos.get("OT3")));}
			if(cuartos.get("OT4")!=null){tanteoCuartos.insertarCuarto("OT4",Integer.parseInt((String) cuartos.get("OT4")));}
			if(cuartos.get("OT5")!=null){tanteoCuartos.insertarCuarto("OT5",Integer.parseInt((String) cuartos.get("OT5")));}
			if(cuartos.get("OT6")!=null){tanteoCuartos.insertarCuarto("OT6",Integer.parseInt((String) cuartos.get("OT6")));}
			
		return tanteoCuartos;
	}
	
	private static ClaseFullBoxscore devolverFullBox(Document box) {
		ClaseFullBoxscore full = new ClaseFullBoxscore();
			
			if(box.get("asistencias")!=null) {full.setAsistencias((Integer)box.get("asistencias"));}
			//full.setAsistencias((Integer)box.get("asistencias"));
			full.setBandeja((Integer)box.get("bandeja"));
			full.setBandejaFallada((Integer)box.get("bandejaFallada"));
			full.setCuarto((String)box.get("cuarto"));
			full.setFaltaPersonalAtaque((Integer)box.get("faltaPersonalAtaque"));
			full.setFaltaPersonalDefensa((Integer)box.get("faltaPersonalDefensa"));
			full.setFaltaPersonalProvocadaEnAtaque((Integer)box.get("faltaPersonalProvocadaEnAtaque"));
			full.setFaltaPersonalProvocadaEnDefensa((Integer)box.get("faltaPersonalProvocadaEnDefensa"));
			full.setFaltaPersonalProvocadaEnTiro((Integer)box.get("faltaPersonalProvocadaEnTiro"));
			full.setFaltaPersonalTiro((Integer)box.get("faltaPersonalTiro"));
			full.setFaltaTecnica((Integer)box.get("faltaTecnica"));
			full.setFaltasPersonales((Integer)box.get("faltasPersonales"));
			full.setFaltasPersonalesProvocadas((Integer)box.get("faltasPersonalesProvocadas"));
			full.setGancho((Integer)box.get("gancho"));
			full.setGanchoFallado((Integer)box.get("ganchoFallado"));
			full.setMasMenos((Integer)box.get("masMenos"));
			full.setMate((Integer)box.get("mate"));
			full.setMateFallado((Integer)box.get("mateFallado"));
			full.setPerdidaBalonPerdido((Integer)box.get("perdidaBalonPerdido"));
			full.setPerdidaFueraBanda((Integer)box.get("perdidaFueraBanda"));
			full.setPerdidaGoaltending((Integer)box.get("pedidaGoalTending"));
			full.setPerdidaMalPase((Integer)box.get("perdidaMalPase"));
			full.setPerdidaPasos((Integer)box.get("perdidaPasos"));
			full.setPerdidaPisarFuera((Integer)box.get("perdidaPisarFuera"));
			full.setPerdidas((Integer)box.get("perdidas"));
			full.setPrimerTiroLibreDentro((Integer)box.get("primerTiroLibreDentro"));
			full.setPrimerTiroLibreFuera((Integer)box.get("primerTiroLibreFuera"));
			full.setPrimerTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get("primerTiroLibrePorcentaje")));
			full.setPrimerTiroLibreTotal((Integer)box.get("primerTiroLibreTotal"));
			full.setPuntos((Integer)box.get("puntos"));
			full.setReboteDefensivo((Integer)box.get("reboteDefensivo"));
			full.setReboteOfensivo((Integer)box.get("reboteOfensivo"));
			full.setRobos((Integer)box.get("robos"));
			full.setSegundoTiroLibreDentro((Integer)box.get("segundoTiroLibreDentro"));
			full.setSegundoTiroLibreFuera((Integer)box.get("segundoTiroLibreFuera"));
			full.setSegundoTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get("segundoTiroLibrePorcentaje")));
			full.setSegundoTiroLibreTotal((Integer)box.get("segundoTiroLibreTotal"));
			full.setSuspension((Integer)box.get("suspensi�n"));
			full.setSuspensionFallado((Integer)box.get("suspensionFallada"));
			full.setTaponRecibido((Integer)box.get("taponRecibido"));
			full.setTaponRecibidoTriple((Integer)box.get("taponRecibidoTriple"));
			full.setTapones((Integer)box.get("tapones"));
			full.setTercerTiroLibreDentro((Integer)box.get("tercerTiroLibreDentro"));
			full.setTercerTiroLibreFuera((Integer)box.get("tercerTiroLibreFuera"));
			full.setTercerTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get("tercerTiroLibrePorcentaje")));
			full.setTercerTiroLibreTotal((Integer)box.get("tercerTiroLiberTotal"));
			full.setTirosCampoIntentados((Integer)box.get("tirosCampoIntentados"));
			full.setTirosCampoMetidos((Integer)box.get("tirosCampoMetidos"));
			full.setTirosCampoPorcentaje((Double)revisarDatoIntegerDouble(box.get("tirosCampoPorcentaje")));
			full.setTirosLibresIntentados((Integer)box.get("tirosLibresIntentados"));
			full.setTirosLibresMetidos((Integer)box.get("tirosLibresMetidos"));
			full.setTirosLibresPorcentaje((Double)revisarDatoIntegerDouble(box.get("tirosLibresPorcentaje")));
			full.setTotalRebotes((Integer)box.get("totalRebotes"));
			full.setTriplesIntentados((Integer)box.get("triplesIntentados"));
			full.setTriplesMetidos((Integer)box.get("triplesMetidos"));
			full.setTriplesPorcentaje((Double)revisarDatoIntegerDouble(box.get("triplesPorcentaje")));
		
		return full;
	}
	
	private static Double revisarDatoIntegerDouble(Object object) {
		Double valor;
		try {
			valor = (Double)object;
		} catch (ClassCastException nfe){
			return new Double(0);
		}
		return valor;
	}
	
	private static ArrayList<ClaseTiros> devolverCartaTiro(ArrayList<Document> lista){
		ArrayList<ClaseTiros> listaTiros = new ArrayList<ClaseTiros>();
			if(lista!=null) {
				for(int i=0;i<lista.size();i++) {
					listaTiros.add(devolverTiros((Document)lista.get(i)));
				}
			}
		return listaTiros;
	}
	
	private static ClaseTiros devolverTiros(Document tiro) {
		ClaseTiros carta =new ClaseTiros();
		
			carta.setDentro((Boolean) tiro.get("dentro"));
			carta.setDistancia((Integer) tiro.get("distancia"));
			carta.setPosicionLeft((String) tiro.get("posicionLeft"));
			carta.setPosicionTop((String) tiro.get("posicionTop"));
			carta.setSituacion((String) tiro.get("situacion"));
			carta.setTiempoRestante((Integer) tiro.get("tiempoRestante"));
			carta.setTipo((String) tiro.get("tipo"));
			carta.setCuarto((String)tiro.get("cuarto"));
		
		return carta;
	}
	
	public static ClaseEstadisticaNormalTotales devolverEstadisticasTotalesJugador(Document stats) {
		ClaseEstadisticaNormalTotales total = new ClaseEstadisticaNormalTotales();
			total.setIdJugador((String)stats.get("idJugador"));
			total.setTemporada((String)stats.get("temporada"));
			total.setTiempo((String)stats.get("tiempo"));
			total.setTiempo((String)stats.get("tiporesultado"));
			total.setPartidosJugados((String)stats.get("G").toString());
			if(total.getPartidosJugados().equals("0")) {
				total.setPartidosJugados("9999999");
			}
			total.setPartidosQuintetoInicial((String)stats.get("GS").toString());
			total.setMinutos((String)stats.get("MP").toString());
			
			total.setTirosCampoMetidos((String)stats.get("FG").toString());
			total.setTirosCampoIntentados((String)stats.get("FGA").toString());
			total.setTirosCampoPorcentaje((String)stats.get("FG%").toString());
			
			total.setTriplesMetidos((String)stats.get("3P").toString());
			total.setTriplesIntentados((String)stats.get("3PA").toString());
			total.setTriplesPorcentaje((String)stats.get("3P%").toString());
			
			total.setDosPuntosMetidos((String)stats.get("2P").toString());
			total.setDosPuntosIntentados((String)stats.get("2PA").toString());
			total.setDosPuntosPorcentaje((String)stats.get("2P%").toString());
			
			total.setTirosLibresMetidos((String)stats.get("FT").toString());
			total.setTirosLibresIntentados((String)stats.get("FTA").toString());
			total.setTirosLibresPorcentaje((String)stats.get("FT%").toString());
			
			total.setReboteOfensivo((String)stats.get("ORB").toString());
			total.setReboteDefensivo((String)stats.get("DRB").toString());
			total.setTotalRebotes((String)stats.get("TRB").toString());
			
			total.setAsistencias((String)stats.get("AST").toString());
			total.setRobos((String)stats.get("STL").toString());
			total.setTapones((String)stats.get("BLK").toString());
			
			total.setPerdidas((String)stats.get("TOV").toString());
			total.setFaltasPersonales((String)stats.get("PF").toString());
			total.setPuntos((String)stats.get("PTS").toString());
			total.calcularMedias();
			
			
		return total;
	}

	public static ClaseJugadorTiros rellenarTirosPartidoJugador(Document partido, String jugador, boolean casa, String t) {
		ClaseJugadorTiros tirosPartido = new ClaseJugadorTiros(); 
			tirosPartido.setSeason(t);
			tirosPartido.setPlayoff((boolean) partido.get("playOff"));
			tirosPartido.setDia((String) partido.get("dia"));
			tirosPartido.setMes((String) partido.get("mes"));
			tirosPartido.setYear((String) partido.get("year"));
			Document equipoLocal = (Document)partido.get("equipoLocal");
			Document equipoVisitante = (Document)partido.get("equipoVisitante");
			if(casa) {
				tirosPartido.setEquipoCon((String)equipoLocal.get("nombreAbreviado"));
				tirosPartido.setEquipoContra((String)equipoVisitante.get("nombreAbreviado"));
				tirosPartido.setListaTiros(devolverCartaTiro((ArrayList<Document>)devolverJugador(jugador,(ArrayList<Document>)equipoLocal.get("jugadores")).get("listaTiros"))); // ListaTiros
			}else {
				tirosPartido.setEquipoContra((String)equipoLocal.get("nombreAbreviado"));
				tirosPartido.setEquipoCon((String)equipoVisitante.get("nombreAbreviado"));
				tirosPartido.setListaTiros(devolverCartaTiro((ArrayList<Document>)devolverJugador(jugador,(ArrayList<Document>)equipoVisitante.get("jugadores")).get("listaTiros"))); // ListaTiros
			}
			
		
		return tirosPartido;
	}
	
	private static Document devolverJugador(String jugador,ArrayList<Document> lista) {
		for(int i=0;i<lista.size();i++) {
			if(jugador.equals((String)lista.get(i).get("id"))) {
				return lista.get(i);
			}
		}
		return null;
	}
}
