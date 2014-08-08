package it.arakne.dbing.magento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;

public class DateUtil {

	private final static Logger LOGGER = Logger.getLogger(DateUtil.class.getName());
	
	/**
	 * Variabile statica che definisce i giorni di vacanza aggiuntivi rispetto quelli
	 * definiti di default dalla classe
	 */
	private static HolidayCalendar<LocalDate> calendar;
	private static DateCalculator<LocalDate> cal;

	public static void main(String[] args) {
		try {
			initBusinessDayBySpan(3);
			System.out.println("+3: " + getBusinessDay());
			
			nextBusinessDay();
			System.out.println("+4: " + getBusinessDay());
			
			nextBusinessDay();
			System.out.println("+5: " + getBusinessDay());

			LocalDate dueDate = getWorkingDaysDate(1);
			System.out.println(dueDate);
			System.out.println(dueDate.toString());
			//System.out.println(decodeData(dueDate));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String decodeData(String inputDate) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat(Config.INPUT_DATE_FORMAT);
		Date myDate = inputFormat.parse(inputDate);
		SimpleDateFormat outputFormat = new SimpleDateFormat(Config.OUTPUT_DATE_FORMAT);
		return outputFormat.format(myDate);
	}

	public static String decodeData(LocalDate inputDate) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat(Config.LOCAL_INPUT_DATE_FORMAT);
		Date myDate = inputFormat.parse(inputDate.toString());
		SimpleDateFormat outputFormat = new SimpleDateFormat(Config.OUTPUT_DATE_FORMAT);
		return outputFormat.format(myDate);
	}

	public static LocalDate getWorkingDaysDate(Integer dueDateBD){
		final Set<LocalDate> holidays = new HashSet<LocalDate>();
		for (int year=2000; year<2100;year++){
			/*aggiungo 26 dicembre di tutti gli anni*/
			holidays.add(new LocalDate(year+"-12-26"));
			/*aggiungo 01 gennaio di tutti gli anni*/
			holidays.add(new LocalDate(year+"-01-01"));
		}
		final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate> (holidays, new LocalDate("2001-01-01"), new LocalDate("2100-12-31"));
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("IT", calendar);
		DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("IT", HolidayHandlerType.FORWARD);
		cal.setStartDate(new LocalDate());
		LocalDate current = cal.getCurrentBusinessDate(); 
		LOGGER.fine("oggi: "+current);
		
		//System.out.println("Current: " + current);
		
		LocalDate dueDate = cal.moveByDays(dueDateBD).getCurrentBusinessDate(); // 4 Sept 06 due to weekend!
		
		//System.out.println("LOCAL DATE: " + dueDate);
		
		LOGGER.fine("oggi + dueDate: "+dueDate);
		return dueDate;
	}

	public static Integer convertBusinessDays(String dueDateBD){
		Integer dueDate = 1;
		if (dueDateBD!=null && !dueDateBD.isEmpty()) {
			try {
				if (dueDateBD.endsWith(Config.ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_SUFFIX)) {
					dueDateBD = dueDateBD.replaceAll(Config.ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_SUFFIX, "");
					dueDate = Integer.valueOf(dueDateBD);
				}
			}
			catch (NumberFormatException e) {
				LOGGER.severe(e.getMessage());
				LOGGER.severe("I business days inseriti non sono validi. settati a 1.");
				e.printStackTrace();
				return dueDate;
			}
		}
		return dueDate;
	}
	
	/*
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * 
	 * Metodi per l'iterazione tra i giorni lavorativi utilizzando uno pseudoiteratore
	 * in grado di calcolare i giorni "successivi" (lavorativi) a partire da un primo span
	 * inserito e avanzando in base a questo
	 * 
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */
	
	/**
	 * Setup dei giorno festivi includendo il 26 dicembre e il 1 gennaio
	 */
	public static void calendarSetup() {
		Set<LocalDate> holidays = new HashSet<LocalDate>();
		for (int year=2000; year<2100;year++){
			holidays.add(new LocalDate(year+"-12-26"));
			holidays.add(new LocalDate(year+"-01-01"));
		}
		
		calendar = new DefaultHolidayCalendar<LocalDate> (holidays, new LocalDate("2001-01-01"), new LocalDate("2100-12-31"));
		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("IT", calendar);
		cal = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("IT", HolidayHandlerType.FORWARD);
	}
	
	/**
	 * Definizione del primo giorno di span per il calcolo dei giorni lavorativi
	 * 
	 * @param dateSpan
	 * @return
	 */
	public static void initBusinessDayBySpan(Integer span) {
		calendarSetup();
		cal.moveByDays(span);
	}
	
	/**
	 * Sposta il calendario al giorno successivo (sempre considerando i giorni lavorativi)
	 * @return
	 */
	public static void nextBusinessDay() {
		cal.moveByDays(1);
	}
	
	/**
	 * Ritorna il giorno lavorativo calcolato
	 * @return
	 */
	public static LocalDate getBusinessDay() {
		return cal.getCurrentBusinessDate();
	}
}
