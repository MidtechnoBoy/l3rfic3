/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.mycompany.mavenproject1.vk.VKCore;
import com.mycompany.mavenproject1.vk.Messager;
import com.mycompany.mavenproject1.dao.LessonDao;
import com.mycompany.mavenproject1.dao.ServiceMessageDao;
import com.mycompany.mavenproject1.dao.TimeTableDao;
import com.mycompany.mavenproject1.dao.TimeTableManagerDao;
import com.mycompany.mavenproject1.entity.Lesson;
import com.mycompany.mavenproject1.entity.ServiceMessage;
import com.mycompany.mavenproject1.entity.TimeTable;
import com.mycompany.mavenproject1.entity.TimeTableManager;
import com.mycompany.mavenproject1.jaxb.JAXBManager;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author Ilya
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
        //JAXBManager.runJAXB();
    }
    
    public static void fillDataBases() {
        TimeTableManagerDao timeTableManagerDao = new TimeTableManagerDao();
        ServiceMessageDao serviceMessageDao = new ServiceMessageDao();
        TimeTableDao timeTableDao = new TimeTableDao();
        LessonDao lessonDao = new LessonDao();
        //Сначала создается нечетная неделя
        TimeTableManager oddTimeTableManager = TimeTableManager.deployBuilder()
                .setWeekTypeByNumber(3)
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("MONDAY")
                        .setDayOfWeekRus("ПОНЕДЕЛЬНИК")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лабораторная")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("4-08")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("РВПРС на Java")
                                .setDescription("Лабораторная")
                                .setLector("Герасимов А.М.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(15, 20))
                                .setEndTime(LocalTime.of(16, 55))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("КПО")
                                .setDescription("Лабораторная")
                                .setLector("Расеева Е.В.")
                                .setAudienceNumber("2-33(2)")
                                .setStartTime(LocalTime.of(17, 05))
                                .setEndTime(LocalTime.of(18, 40))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("TUESDAY")
                        .setDayOfWeekRus("ВТОРНИК")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Сети и телекоммуникации")
                                .setDescription("Лабораторная\n"
                                        + "(!) Пара проходит в 1-ом корпусе. Рекомендуемый транспорт до ул. Льва Толстого, 23:\n "
                                        + "• Автобус 47\n• Трамвай 20")
                                .setLector("Лысиков А.А.")
                                .setAudienceNumber("441")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Электроника")
                                .setDescription("Практика\n"
                                        + "(!) Пара проходит в 1-ом корпусе. Рекомендуемый транспорт до ул. Льва Толстого, 23:\n "
                                        + "• Автобус 47\n• Трамвай 20")
                                .setLector("Арефьев А.С.")
                                .setAudienceNumber("335")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("WEDNESDAY")
                        .setDayOfWeekRus("СРЕДА")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("КПО")
                                .setDescription("Лекция")
                                .setLector("Мостовой Я.А.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("РВПРС на Java")
                                .setDescription("Лекция")
                                .setLector("Герасимов А.М.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лекция")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("2-01")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("THURSDAY")
                        .setDayOfWeekRus("ЧЕТВЕРГ")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Математическое программирование")
                                .setDescription("Лабораторная")
                                .setLector("Аронов В.Ю.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лабораторная")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("5-10")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("FRIDAY")
                        .setDayOfWeekRus("ПЯТНИЦА")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Правоведение")
                                .setDescription("Лекция")
                                .setLector("Фоменко Р.В.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Математическое программирование")
                                .setDescription("Лекция ДЕДА. Рекомендуется смотреть на ЭВМ")
                                .setLector("Тарасов В.Н.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("КПО")
                                .setDescription("Лабораторная")
                                .setLector("Расеева Е.В.")
                                .setAudienceNumber("4-08")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .build();
        TimeTableManager evenTimeTableManager = TimeTableManager.deployBuilder()
                .setWeekTypeByNumber(4)
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("MONDAY")
                        .setDayOfWeekRus("ПОНЕДЕЛЬНИК")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Правоведение")
                                .setDescription("Практика")
                                .setLector("Фоменко Р.В.")
                                .setAudienceNumber("1-12")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("РВПРС на Java")
                                .setDescription("Лабораторная")
                                .setLector("Герасимов А.М.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(15, 20))
                                .setEndTime(LocalTime.of(16, 55))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("TUESDAY")
                        .setDayOfWeekRus("ВТОРНИК")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Сети и телекоммуникации")
                                .setDescription("Лабораторная\n"
                                        + "(!) Пара проходит в 1-ом корпусе. Рекомендуемый транспорт до ул. Льва Толстого, 23:\n "
                                        + "• Автобус 47\n• Трамвай 20")
                                .setLector("Лысиков А.А.")
                                .setAudienceNumber("441")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Электроника")
                                .setDescription("Лабораторная\n"
                                        + "(!) Пара проходит в 1-ом корпусе. Рекомендуемый транспорт до ул. Льва Толстого, 23:\n "
                                        + "• Автобус 47\n• Трамвай 20")
                                .setLector("Арефьев А.С.")
                                .setAudienceNumber("335")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("WEDNESDAY")
                        .setDayOfWeekRus("СРЕДА")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("КПО")
                                .setDescription("Лекция")
                                .setLector("Мостовой Я.А.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("РВПРС на Java")
                                .setDescription("Лекция")
                                .setLector("Герасимов А.М.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лекция")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("2-01")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("THURSDAY")
                        .setDayOfWeekRus("ЧЕТВЕРГ")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Математическое программирование")
                                .setDescription("Лабораторная")
                                .setLector("Аронов В.Ю.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лабораторная")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("5-10")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("FRIDAY")
                        .setDayOfWeekRus("ПЯТНИЦА")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Базы данных")
                                .setDescription("Лабораторная")
                                .setLector("Захарова О.И.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("КПО")
                                .setDescription("Лабораторная")
                                .setLector("Расеева Е.В.")
                                .setAudienceNumber("4-08")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("SATURDAY")
                        .setDayOfWeekRus("СУББОТА")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Сети и телекоммуникации")
                                .setDescription("Лекция")
                                .setLector("Лысиков А.А.")
                                .setAudienceNumber("314")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("Электроника")
                                .setDescription("Лекция")
                                .setLector("Арефьев А.С.")
                                .setAudienceNumber("308")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .build())
                .build();
        StringBuilder infoBuilder = new StringBuilder()
                .append("| Информация о боте L3RFIC3 |")
                .append("\nСписок доступных команд ")
                .append("\n(i) синтаксис команд указан в квадратных скобках, например: [сообщение]")
                .append("\n\n• [Лерфис инфо], [l3 info] - информация о боте")
                .append("\n• [Лерфис расписание], [l3 tt*] - бот скинет полное расписание с учетом четности недели")
                .append("\n• [Лерфис расписание на сегодня], [l3 tt] - бот скинет расписание на сегодня")
                .append("\n• [Лерфис расписание на завтра], [l3 tt+] - бот скинет расписание на завтра")
                .append("\n• [Лерфис время], [l3 tm] - бот подскажет, сколько времени осталось до начала пары и куда надо идти");
        ServiceMessage msg = ServiceMessage.deployBuilder()
                .setKey("info")
                .setValue(infoBuilder.toString())
                .build();
        serviceMessageDao.save(msg);
        serviceMessageDao.save(ServiceMessage.deployBuilder()
                .setKey("no_tt")
                .setValue("Сегодня нет пар или они уже закончились")
                .build());
        serviceMessageDao.save(ServiceMessage.deployBuilder()
                .setKey("no_tt+")
                .setValue("Завтра выходной. Желаю тебе провести его с пользой ;)")
                .build());
        timeTableManagerDao.save(oddTimeTableManager);
        timeTableManagerDao.save(evenTimeTableManager);
    }
    
    public static void connect() {
        try {
            //fillDataBases();
            //testCommands();
            runBot();
        } catch (InterruptedException e) {
            System.out.println("Возникла ошибка: ");
            e.printStackTrace();
        }
    }
    
    private static void runBot() throws InterruptedException {
        final int RECONNECT_TIME = 10000;
        ExecutorService exec = Executors.newCachedThreadPool();
        VKCore vkCore = new VKCore();
        Messager messager = new Messager(vkCore);
        while (true) {
            Thread.sleep(300);
            try {
                if (!messager.isMessageNull()) {
                    exec.execute(messager);
                }

            } catch (ClientException | ApiException e) {
                System.out.println("Возникли проблемы");
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
    
}
