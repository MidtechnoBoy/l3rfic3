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
        //������� ��������� �������� ������
        TimeTableManager oddTimeTableManager = TimeTableManager.deployBuilder()
                .setWeekTypeByNumber(3)
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("MONDAY")
                        .setDayOfWeekRus("�����������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("4-08")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("����� �� Java")
                                .setDescription("������������")
                                .setLector("��������� �.�.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(15, 20))
                                .setEndTime(LocalTime.of(16, 55))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���")
                                .setDescription("������������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("2-33(2)")
                                .setStartTime(LocalTime.of(17, 05))
                                .setEndTime(LocalTime.of(18, 40))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("TUESDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� � ����������������")
                                .setDescription("������������\n"
                                        + "(!) ���� �������� � 1-�� �������. ������������� ��������� �� ��. ���� ��������, 23:\n "
                                        + "� ������� 47\n� ������� 20")
                                .setLector("������� �.�.")
                                .setAudienceNumber("441")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�����������")
                                .setDescription("��������\n"
                                        + "(!) ���� �������� � 1-�� �������. ������������� ��������� �� ��. ���� ��������, 23:\n "
                                        + "� ������� 47\n� ������� 20")
                                .setLector("������� �.�.")
                                .setAudienceNumber("335")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("WEDNESDAY")
                        .setDayOfWeekRus("�����")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���")
                                .setDescription("������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("����� �� Java")
                                .setDescription("������")
                                .setLector("��������� �.�.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("2-01")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("THURSDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�������������� ����������������")
                                .setDescription("������������")
                                .setLector("������ �.�.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("5-10")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("FRIDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("������������")
                                .setDescription("������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�������������� ����������������")
                                .setDescription("������ ����. ������������� �������� �� ���")
                                .setLector("������� �.�.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���")
                                .setDescription("������������")
                                .setLector("������� �.�.")
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
                        .setDayOfWeekRus("�����������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("������������")
                                .setDescription("��������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("1-12")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("����� �� Java")
                                .setDescription("������������")
                                .setLector("��������� �.�.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(15, 20))
                                .setEndTime(LocalTime.of(16, 55))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("TUESDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� � ����������������")
                                .setDescription("������������\n"
                                        + "(!) ���� �������� � 1-�� �������. ������������� ��������� �� ��. ���� ��������, 23:\n "
                                        + "� ������� 47\n� ������� 20")
                                .setLector("������� �.�.")
                                .setAudienceNumber("441")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�����������")
                                .setDescription("������������\n"
                                        + "(!) ���� �������� � 1-�� �������. ������������� ��������� �� ��. ���� ��������, 23:\n "
                                        + "� ������� 47\n� ������� 20")
                                .setLector("������� �.�.")
                                .setAudienceNumber("335")
                                .setStartTime(LocalTime.of(13, 35))
                                .setEndTime(LocalTime.of(15, 10))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("WEDNESDAY")
                        .setDayOfWeekRus("�����")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���")
                                .setDescription("������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("����� �� Java")
                                .setDescription("������")
                                .setLector("��������� �.�.")
                                .setAudienceNumber("2-08")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("2-01")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("THURSDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�������������� ����������������")
                                .setDescription("������������")
                                .setLector("������ �.�.")
                                .setAudienceNumber("2-35(2)")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(false)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("5-10")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(false)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("FRIDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� ������")
                                .setDescription("������������")
                                .setLector("�������� �.�.")
                                .setAudienceNumber("2-07")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���")
                                .setDescription("������������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("4-08")
                                .setStartTime(LocalTime.of(11, 40))
                                .setEndTime(LocalTime.of(13, 15))
                                .setIsRemote(true)
                                .build())
                        .build())
                .addTimeTable(TimeTable.deployBuilder()
                        .setDayOfWeek("SATURDAY")
                        .setDayOfWeekRus("�������")
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("���� � ����������������")
                                .setDescription("������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("314")
                                .setStartTime(LocalTime.of(8, 10))
                                .setEndTime(LocalTime.of(9, 45))
                                .setIsRemote(true)
                                .build())
                        .addLesson(Lesson.deployBuilder()
                                .setTitle("�����������")
                                .setDescription("������")
                                .setLector("������� �.�.")
                                .setAudienceNumber("308")
                                .setStartTime(LocalTime.of(9, 55))
                                .setEndTime(LocalTime.of(11, 30))
                                .setIsRemote(true)
                                .build())
                        .build())
                .build();
        StringBuilder infoBuilder = new StringBuilder()
                .append("| ���������� � ���� L3RFIC3 |")
                .append("\n������ ��������� ������ ")
                .append("\n(i) ��������� ������ ������ � ���������� �������, ��������: [���������]")
                .append("\n\n� [������ ����], [l3 info] - ���������� � ����")
                .append("\n� [������ ����������], [l3 tt*] - ��� ������ ������ ���������� � ������ �������� ������")
                .append("\n� [������ ���������� �� �������], [l3 tt] - ��� ������ ���������� �� �������")
                .append("\n� [������ ���������� �� ������], [l3 tt+] - ��� ������ ���������� �� ������")
                .append("\n� [������ �����], [l3 tm] - ��� ���������, ������� ������� �������� �� ������ ���� � ���� ���� ����");
        ServiceMessage msg = ServiceMessage.deployBuilder()
                .setKey("info")
                .setValue(infoBuilder.toString())
                .build();
        serviceMessageDao.save(msg);
        serviceMessageDao.save(ServiceMessage.deployBuilder()
                .setKey("no_tt")
                .setValue("������� ��� ��� ��� ��� ��� �����������")
                .build());
        serviceMessageDao.save(ServiceMessage.deployBuilder()
                .setKey("no_tt+")
                .setValue("������ ��������. ����� ���� �������� ��� � ������� ;)")
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
            System.out.println("�������� ������: ");
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
                System.out.println("�������� ��������");
                System.out.println("��������� ���������� ����� " + RECONNECT_TIME / 1000 + " ������");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
    
}
