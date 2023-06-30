package Mapper;

import java.util.ArrayList;

import org.bson.Document;

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

public class MapJugadorEstadisticas extends Atributos{

	
	public static ClasePartido rellenarJuegadorEstadisticas(Document partido, String id,String contrario) {
		
		ClasePartido match = new ClasePartido();

			match.setHora((String) partido.get(ATRIBUTO_PARTIDO_HORA));
			match.setDia((String) partido.get(ATRIBUTO_PARTIDO_DIA));
			match.setMes((String) partido.get(ATRIBUTO_PARTIDO_MES));
			match.setYear((String) partido.get(ATRIBUTO_PARTIDO_YEAR));
			match.setPlayOff((boolean) partido.get(ATRIBUTO_PARTIDO_PLAYOFF));
			System.out.println(match.getDia()+" / "+match.getMes()+" / "+match.getYear()+" Contrario: "+contrario);
			if(ATRIBUTO_VISITANTE.equals(contrario)) {
				match.setEquipoVisitante(rellenarEquipoJugadorEstadistica((Document)partido.get(ATRIBUTO_PARTIDO_EQUIPO_VISITANTE),id)); //EquipoVisitante
			}else if(ATRIBUTO_LOCAL.equals(contrario)) {
				match.setEquipoLocal(rellenarEquipoJugadorEstadistica((Document)partido.get(ATRIBUTO_PARTIDO_EQUIPO_LOCAL),id));	//EquipoLocal
			}
		
		return match;
	}
	
	@SuppressWarnings("unchecked")
	private static ClaseEquipo rellenarEquipoJugadorEstadistica(Document team,String id){
		ClaseEquipo equipo = new ClaseEquipo();
		
			equipo.setJugadores(devolverJugadores((ArrayList<Document>)team.get(ATRIBUTO_EQUIPO_JUGADORES),id)); // Array Jugadores
			equipo.setNombre((String) team.get(ATRIBUTO_EQUIPO_NOMBRE));
			equipo.setTanteo((String) team.get(ATRIBUTO_EQUIPO_TANTEO));
			equipo.setTanteoCuartos(devolverCuartos((Document)team.get(ATRIBUTO_EQUIPO_TANTEO_CUARTOS))); // Tanteo Cuartos
			equipo.setVictorias((String) team.get(ATRIBUTO_EQUIPO_VICTORIAS));
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
	
	@SuppressWarnings("unchecked")
	private static ClaseJugador devolverJugador(Document player,String id) {
		ClaseJugador jugador = new ClaseJugador();
		
		if(id.equals((String) player.get(ATRIBUTO_JUGADOR_ID))) {
			jugador.setBoxscore(devolverEstadisticaNormal((Document) player.get(ATRIBUTO_JUGADOR_BOXSCORE))); // BoxScore
			jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_CUARTO1))); // Cuartos	
			jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_CUARTO2)));
			jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_CUARTO3)));
			jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_CUARTO4)));
			if(player.get(ATRIBUTO_JUGADOR_OVER1)!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_OVER1)));}
			if(player.get(ATRIBUTO_JUGADOR_OVER2)!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_OVER2)));}
			if(player.get(ATRIBUTO_JUGADOR_OVER3)!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_OVER3)));}
			if(player.get(ATRIBUTO_JUGADOR_OVER4)!=null) {jugador.addCuartoJugador(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_OVER4)));}
			jugador.setEstadisticaAvanzada(devolverEstadisticaAvanzada((Document)player.get(ATRIBUTO_JUGADOR_ESTADISTICA_AZANZADA))); // EstadisticaAvanzada
			jugador.setInicio((Boolean) player.get(ATRIBUTO_JUGADOR_INICIO));
			jugador.setListaTiros(devolverCartaTiro((ArrayList<Document>)player.get(ATRIBUTO_JUGADOR_LISTA_TIROS))); // ListaTiros
			jugador.setSegundos((Integer) player.get(ATRIBUTO_JUGADOR_SEGUNDOS));
			jugador.setTotalPartido(devolverFullBox((Document)player.get(ATRIBUTO_JUGADOR_TOTAL_PARTIDO))); // TotalPartido
			
			return jugador;
		}
		
		return null;
	}
	
	private static ClaseEstadisticaAvanzada devolverEstadisticaAvanzada(Document advan){
		ClaseEstadisticaAvanzada avanzadas = new ClaseEstadisticaAvanzada();
		
		avanzadas.setFANTASY((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_FANTASY)));
		avanzadas.setFTARate((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_FTARATE)));
		avanzadas.setNETRTG((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_NETRTG)));
		avanzadas.setAssistPercentage((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_ASSISTPERCENTAGE)));
		avanzadas.setBlockPercentage((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_BLOCKPERCENTAGE)));
		avanzadas.setDefensiveRating((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_DEFENSIVERATING)));
		avanzadas.setDefensiveReboundPer((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_DEFENSIVEREBOUNDPER)));
		avanzadas.setEffectiveGoalPer((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_EFFECTIVEGOALPER)));
		avanzadas.setFreeThrowRate((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_FREETHROWRATE)));
		avanzadas.setOffensiveRating((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_OFFENSIVERATING)));
		avanzadas.setOffensiveReboundPer((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_OFFENSIVEREBOUNDPER)));
		avanzadas.setPerPTS1((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS1)));
		avanzadas.setPerPTS2((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS2)));
		avanzadas.setPerPTS2PT((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS2PT)));
		avanzadas.setPerPTS2PTM((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS2PTM)));
		avanzadas.setPerPTS3((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS3)));
		avanzadas.setPerPTS3PT((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS3PT)));
		avanzadas.setPerPTS3PTM((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_PERPTS3PTM)));
		avanzadas.setStealPercentage((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_STEALPERCENTAGE)));
		avanzadas.setThreePointRate((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_THREEPOINTRATE)));
		avanzadas.setTotalReboundPer((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_TOTALREBOUNDPER)));
		avanzadas.setTrueShootPer((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_TRUESHOOTPER)));
		avanzadas.setTurnoverPercentage((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_TURNOVERPERCENTAGE)));
		avanzadas.setUsagePercentage((Double)revisarDatoIntegerDouble(advan.get(ATRIBUTO_ADVANCE_USAGEPERCENTAGE)));
		
		return avanzadas;
	}
	
	private static ClaseEstadisticaNormal devolverEstadisticaNormal(Document nor) {
		ClaseEstadisticaNormal normal = new ClaseEstadisticaNormal();
		
		normal.setAsistencias((Integer) nor.get(ATRIBUTO_BOX_ASISTENCIAS));
		normal.setCuarto((String) nor.get(ATRIBUTO_BOX_CUARTO));
		normal.setFaltasPersonales((Integer) nor.get(ATRIBUTO_BOX_FALTAS_PERSONALES));
		normal.setPerdidas((Integer) nor.get(ATRIBUTO_BOX_PERDIDAS));
		normal.setPuntos((Integer) nor.get(ATRIBUTO_BOX_PUNTOS));
		normal.setReboteDefensivo((Integer) nor.get(ATRIBUTO_BOX_REBOTE_DEFENSIVO));
		normal.setReboteOfensivo((Integer) nor.get(ATRIBUTO_BOX_REBOTE_OFENSIVO));
		normal.setRobos((Integer) nor.get(ATRIBUTO_BOX_ROBOS));
		normal.setTapones((Integer) nor.get(ATRIBUTO_BOX_TAPONES));
		normal.setTirosCampoIntentados((Integer) nor.get(ATRIBUTO_BOX_TIROS_CAMPO_INTENTADOS));
		normal.setTirosCampoMetidos((Integer) nor.get(ATRIBUTO_BOX_TIROS_CAMPO_METIDOS));
		normal.setTirosCampoPorcentaje((Double)revisarDatoIntegerDouble(nor.get(ATRIBUTO_BOX_TIROS_CAMPO_PORCENTAJE)));
		normal.setTirosLibresIntentados((Integer) nor.get(ATRIBUTO_BOX_TIROS_LIBRES_INTENTADOS));
		normal.setTirosLibresMetidos((Integer) nor.get(ATRIBUTO_BOX_TIROS_LIBRES_METIDOS));
		normal.setTirosLibresPorcentaje((Double)revisarDatoIntegerDouble(nor.get(ATRIBUTO_BOX_TIROS_LIBRES_PORCENTAJE)));
		normal.setTotalRebotes((Integer) nor.get(ATRIBUTO_BOX_TOTAL_REBOTES));
		normal.setTriplesIntentados((Integer) nor.get(ATRIBUTO_BOX_TRIPLES_INTENTADOS));
		normal.setTriplesMetidos((Integer) nor.get(ATRIBUTO_BOX_TRIPLES_METIDOS));
		normal.setTriplesPorcentaje((Double)revisarDatoIntegerDouble(nor.get(ATRIBUTO_BOX_TRIPLES_PORCENTAJE)));
		
		return normal;
	}

	private static ClaseTanteoCuartos devolverCuartos(Document cuartos) {
		ClaseTanteoCuartos tanteoCuartos = new ClaseTanteoCuartos();
		
		tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_PRIMERO,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_PRIMERO)));
		tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_SEGUNDO,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_SEGUNDO)));
		tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_TERCERO,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_TERCERO)));
		tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_CUARTO,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_CUARTO)));
		if(cuartos.get(ATRIBUTO_CUARTOS_OT1)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT1,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT1)));}
		if(cuartos.get(ATRIBUTO_CUARTOS_OT2)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT2,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT2)));}
		if(cuartos.get(ATRIBUTO_CUARTOS_OT3)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT3,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT3)));}
		if(cuartos.get(ATRIBUTO_CUARTOS_OT4)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT4,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT4)));}
		if(cuartos.get(ATRIBUTO_CUARTOS_OT5)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT5,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT5)));}
		if(cuartos.get(ATRIBUTO_CUARTOS_OT6)!=null){tanteoCuartos.insertarCuarto(ATRIBUTO_CUARTOS_OT6,Integer.parseInt((String) cuartos.get(ATRIBUTO_CUARTOS_OT6)));}
			
		return tanteoCuartos;
	}
	
	private static ClaseFullBoxscore devolverFullBox(Document box) {
		ClaseFullBoxscore full = new ClaseFullBoxscore();
			
		if(box.get(ATRIBUTO_FULLBOX_ASISTENCIAS)!=null) {full.setAsistencias((Integer)box.get(ATRIBUTO_FULLBOX_ASISTENCIAS));}
		//full.setAsistencias((Integer)box.get("asistencias"));
		full.setBandeja((Integer)box.get(ATRIBUTO_FULLBOX_BANDEJA));
		full.setBandejaFallada((Integer)box.get(ATRIBUTO_FULLBOX_BANDEJA_FALLADA));
		full.setCuarto((String)box.get(ATRIBUTO_FULLBOX_CUARTO));
		full.setFaltaPersonalAtaque((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_ATAQUE));
		full.setFaltaPersonalDoble((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_DOBLE));
		full.setFaltaPersonalDefensa((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_DEFENSA));
		full.setFaltaPersonalProvocadaEnAtaque((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_PROVOCADA_ATAQUE));
		full.setFaltaPersonalProvocadaEnDefensa((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_PROVOCADA_DEFENSA));
		full.setFaltaPersonalProvocadaEnTiro((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_PROVOCADA_EN_TIRO));
		full.setFaltaPersonalTiro((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_PERSONAL_TIRO));
		full.setFaltaTecnica((Integer)box.get(ATRIBUTO_FULLBOX_FALTA_TECNICA));
		full.setFaltasPersonales((Integer)box.get(ATRIBUTO_FULLBOX_FALTAS_PERSONALES));
		full.setFaltasPersonalesProvocadas((Integer)box.get(ATRIBUTO_FULLBOX_FALTAS_PERSONALES_PROVOCADAS));
		full.setGancho((Integer)box.get(ATRIBUTO_FULLBOX_GANCHO));
		full.setGanchoFallado((Integer)box.get(ATRIBUTO_FULLBOX_GANCHO_FALLADO));
		full.setMasMenos((Integer)box.get(ATRIBUTO_FULLBOX_MAS_MENOS));
		full.setMate((Integer)box.get(ATRIBUTO_FULLBOX_MATE));
		full.setMateFallado((Integer)box.get(ATRIBUTO_FULLBOX_MATE_FALLADO));
		full.setPerdidaBalonPerdido((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_BALON_PERDIDO));
		full.setPerdidaFueraBanda((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_FUERA_BANDA));
		full.setPerdidaGoaltending((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_GOAL_TENDING));
		full.setPerdidaMalPase((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_MAL_PASE));
		full.setPerdidaPasos((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_PASOS));
		full.setPerdidaPisarFuera((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_PISAR_FUERA));
		full.setPerdidaCampoAtras((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_CAMPO_ATRAS));
		full.setPerdidaOtros((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_OTROS));
		full.setPerdidaTresSegundos((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_TRES_SEGUNDOS));
		full.setPerdidaFalta((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_FALTA));
		full.setPerdidaDobles((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_DOBLES));
		full.setPerdidaPie((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDA_PIE));
		full.setPerdidas((Integer)box.get(ATRIBUTO_FULLBOX_PERDIDAS));
		full.setPrimerTiroLibreDentro((Integer)box.get(ATRIBUTO_FULLBOX_PRIMER_TIRO_LIBRE_DENTRO));
		full.setPrimerTiroLibreFuera((Integer)box.get(ATRIBUTO_FULLBOX_PRIMER_TIRO_LIBRE_FUERA));
		full.setPrimerTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_PRIMER_TIRO_LIBRE_PORCENTAJE)));
		full.setPrimerTiroLibreTotal((Integer)box.get(ATRIBUTO_FULLBOX_PRIMER_TIRO_LIBRE_TOTAL));
		full.setPuntos((Integer)box.get(ATRIBUTO_FULLBOX_PUNTOS));
		full.setReboteDefensivo((Integer)box.get(ATRIBUTO_FULLBOX_REBOTE_DEFENSIVO));
		full.setReboteOfensivo((Integer)box.get(ATRIBUTO_FULLBOX_REBOTE_DEFENSIVO));
		full.setRobos((Integer)box.get(ATRIBUTO_FULLBOX_ROBOS));
		full.setSegundoTiroLibreDentro((Integer)box.get(ATRIBUTO_FULLBOX_SEGUNDO_TIRO_LIBRE_DENTRO));
		full.setSegundoTiroLibreFuera((Integer)box.get(ATRIBUTO_FULLBOX_SEGUNDO_TIRO_LIBRE_FUERA));
		full.setSegundoTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_SEGUNDO_TIRO_LIBRE_PORCENTAJE)));
		full.setSegundoTiroLibreTotal((Integer)box.get(ATRIBUTO_FULLBOX_SEGUNDO_TIRO_LIBRE_TOTAL));
		full.setSuspension((Integer)box.get(ATRIBUTO_FULLBOX_SUSPENSION));
		full.setSuspensionFallado((Integer)box.get(ATRIBUTO_FULLBOX_SUSPENSION_FALLADA));
		full.setTaponRecibido((Integer)box.get(ATRIBUTO_FULLBOX_TAPON_RECIBIDO));
		full.setTaponRecibidoTriple((Integer)box.get(ATRIBUTO_FULLBOX_TAPON_RECIBIDO_TRIPLE));
		full.setTapones((Integer)box.get(ATRIBUTO_FULLBOX_TAPONES));
		full.setTercerTiroLibreDentro((Integer)box.get(ATRIBUTO_FULLBOX_TERCER_TIRO_LIBRE_DENTRO));
		full.setTercerTiroLibreFuera((Integer)box.get(ATRIBUTO_FULLBOX_TERCER_TIRO_LIBRE_FUERA));
		full.setTercerTiroLibrePorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_TERCER_TIRO_LIBRE_PORCENTAJE)));
		full.setTercerTiroLibreTotal((Integer)box.get(ATRIBUTO_FULLBOX_TERCER_TIRO_LIBRE_TOTAL));
		full.setTirosCampoIntentados((Integer)box.get(ATRIBUTO_FULLBOX_TIROS_CAMPO_INTENTADOS));
		full.setTirosCampoMetidos((Integer)box.get(ATRIBUTO_FULLBOX_TIROS_CAMPO_METIDOS));
		full.setTirosCampoPorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_TIROS_CAMPO_PORCENTAJE)));
		full.setTirosLibresIntentados((Integer)box.get(ATRIBUTO_FULLBOX_TIROS_LIBRES_INTENTADOS));
		full.setTirosLibresMetidos((Integer)box.get(ATRIBUTO_FULLBOX_TIROS_LIBRES_METIDOS));
		full.setTirosLibresPorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_TIROS_LIBRES_PORCENTAJE)));
		full.setTotalRebotes((Integer)box.get(ATRIBUTO_FULLBOX_TOTAL_REBOTES));
		full.setTriplesIntentados((Integer)box.get(ATRIBUTO_FULLBOX_TRIPLES_INTENTADOS));
		full.setTriplesMetidos((Integer)box.get(ATRIBUTO_FULLBOX_TRIPLES_METIDOS));
		full.setTriplesPorcentaje((Double)revisarDatoIntegerDouble(box.get(ATRIBUTO_FULLBOX_TRIPLES_PORCENTAJE)));
		
		return full;
	}
	
	@SuppressWarnings("removal")
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
		
		carta.setDentro((Boolean) tiro.get(ATRIBUTO_TIRO_DENTRO));
		carta.setDistancia((Integer) tiro.get(ATRIBUTO_TIRO_DISTANCIA));
		carta.setPosicionLeft((String) tiro.get(ATRIBUTO_TIRO_POSICION_LEFT));
		carta.setPosicionTop((String) tiro.get(ATRIBUTO_TIRO_POSICION_TOP));
		carta.setCuarto((String)tiro.get(ATRIBUTO_TIRO_CUARTO));
		carta.setTanteo((String) tiro.get(ATRIBUTO_TIRO_TANTEO));
		carta.setTanteoEquipo((String) tiro.get(ATRIBUTO_TIRO_TANTEOEQUIPO));
		carta.setTanteoRival((String) tiro.get(ATRIBUTO_TIRO_TANTEORIVAL));
		carta.setSituacionAntes((String) tiro.get(ATRIBUTO_TIRO_SITUACIONANTES));
		carta.setSituacionDespues((String) tiro.get(ATRIBUTO_TIRO_SITUACIONDESPUES));
		carta.setTiempoRestante((Integer) tiro.get(ATRIBUTO_TIRO_TIEMPO_RESTANTE));
		carta.setTipo((String) tiro.get(ATRIBUTO_TIRO_TIPO));
		return carta;
	}
	
	public static ClaseEstadisticaNormalTotales devolverEstadisticasTotalesJugador(Document stats) {
		ClaseEstadisticaNormalTotales total = new ClaseEstadisticaNormalTotales();
		total.setIdJugador((String)stats.get(ATRIBUTO_STAT_ID_JUGADOR));
		total.setTemporada((String)stats.get(ATRIBUTO_STAT_TEMPORADA));
		total.setTiempo((String)stats.get(ATRIBUTO_STAT_TIEMPO));
		total.setTiempo((String)stats.get(ATRIBUTO_STAT_TIPO_RESULTADO));
		total.setPartidosJugados((String)stats.get(ATRIBUTO_STAT_G).toString());
		if(total.getPartidosJugados().equals("0")) {
			total.setPartidosJugados("9999999");
		}
		total.setPartidosQuintetoInicial((String)stats.get(ATRIBUTO_STAT_GS).toString());
		total.setMinutos((String)stats.get(ATRIBUTO_STAT_MP).toString());
		
		total.setTirosCampoMetidos((String)stats.get(ATRIBUTO_STAT_FG).toString());
		total.setTirosCampoIntentados((String)stats.get(ATRIBUTO_STAT_FGA).toString());
		total.setTirosCampoPorcentaje((String)stats.get(ATRIBUTO_STAT_FGP).toString());
		
		total.setTriplesMetidos((String)stats.get(ATRIBUTO_STAT_3P).toString());
		total.setTriplesIntentados((String)stats.get(ATRIBUTO_STAT_3PA).toString());
		total.setTriplesPorcentaje((String)stats.get(ATRIBUTO_STAT_3PP).toString());
		
		total.setDosPuntosMetidos((String)stats.get(ATRIBUTO_STAT_2P).toString());
		total.setDosPuntosIntentados((String)stats.get(ATRIBUTO_STAT_2PA).toString());
		total.setDosPuntosPorcentaje((String)stats.get(ATRIBUTO_STAT_2PP).toString());
		
		total.setTirosLibresMetidos((String)stats.get(ATRIBUTO_STAT_FT).toString());
		total.setTirosLibresIntentados((String)stats.get(ATRIBUTO_STAT_FTA).toString());
		total.setTirosLibresPorcentaje((String)stats.get(ATRIBUTO_STAT_FTP).toString());
		
		total.setReboteOfensivo((String)stats.get(ATRIBUTO_STAT_ORB).toString());
		total.setReboteDefensivo((String)stats.get(ATRIBUTO_STAT_DRB).toString());
		total.setTotalRebotes((String)stats.get(ATRIBUTO_STAT_TRB).toString());
		
		total.setAsistencias((String)stats.get(ATRIBUTO_STAT_AST).toString());
		total.setRobos((String)stats.get(ATRIBUTO_STAT_STL).toString());
		total.setTapones((String)stats.get(ATRIBUTO_STAT_BLK).toString());
		
		total.setPerdidas((String)stats.get(ATRIBUTO_STAT_TOV).toString());
		total.setFaltasPersonales((String)stats.get(ATRIBUTO_STAT_PF).toString());
		total.setPuntos((String)stats.get(ATRIBUTO_STAT_PTS).toString());
		total.calcularMedias();
			
			
		return total;
	}

	@SuppressWarnings("unchecked")
	public static ClaseJugadorTiros rellenarTirosPartidoJugador(Document partido, String jugador, boolean casa, String t) {
		ClaseJugadorTiros tirosPartido = new ClaseJugadorTiros(); 
		tirosPartido.setSeason(t);
		tirosPartido.setPlayoff((boolean) partido.get(ATRIBUTO_JUGTIRO_PLAYOFF));
		tirosPartido.setDia((String) partido.get(ATRIBUTO_JUGTIRO_DIA));
		tirosPartido.setMes((String) partido.get(ATRIBUTO_JUGTIRO_MES));
		tirosPartido.setYear((String) partido.get(ATRIBUTO_JUGTIRO_YEAR));
		Document equipoLocal = (Document)partido.get(ATRIBUTO_JUGTIRO_EQUIPO_LOCAL);
		Document equipoVisitante = (Document)partido.get(ATRIBUTO_JUGTIRO_EQUIPO_VISITANTE);
		if(casa) {
			tirosPartido.setEquipoCon((String)equipoLocal.get(ATRIBUTO_JUGTIRO_NOMBRE_ABREVIADO));
			tirosPartido.setEquipoContra((String)equipoVisitante.get(ATRIBUTO_JUGTIRO_NOMBRE_ABREVIADO));
			tirosPartido.setListaTiros(devolverCartaTiro((ArrayList<Document>)devolverJugador(jugador,(ArrayList<Document>)equipoLocal.get(ATRIBUTO_JUGTIRO_JUGADORES)).get(ATRIBUTO_JUGTIRO_LISTA_TIROS))); // ListaTiros
		}else {
			tirosPartido.setEquipoContra((String)equipoLocal.get(ATRIBUTO_JUGTIRO_NOMBRE_ABREVIADO));
			tirosPartido.setEquipoCon((String)equipoVisitante.get(ATRIBUTO_JUGTIRO_NOMBRE_ABREVIADO));
			tirosPartido.setListaTiros(devolverCartaTiro((ArrayList<Document>)devolverJugador(jugador,(ArrayList<Document>)equipoVisitante.get(ATRIBUTO_JUGTIRO_JUGADORES)).get(ATRIBUTO_JUGTIRO_LISTA_TIROS))); // ListaTiros
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
