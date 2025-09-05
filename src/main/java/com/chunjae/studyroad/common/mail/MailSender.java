package com.chunjae.studyroad.common.mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.chunjae.studyroad.common.exception.*;
import com.chunjae.studyroad.common.util.FileUtils;

/**
 * jakarta.mail 라이브러리 기반 이메일을 보내는 전송 메소드 취급
 */
public class MailSender {

    // 메일을 보낼 계정 정보 (mail.properties 에서 정보 가져옴)
    private String username; // 발신자 이메일
    private String password; // 발신자 비밀번호

    // 메일 아이디, 비밀번호 정보를 불러오고, 이메일 전송을 위한 설정을 담을 Property
    private final Properties props = new Properties();

    // 인스턴스
    private static final MailSender INSTANCE = new MailSender();

    // 생성자 접근 제한
    private MailSender() {
        init();
    }

    // 인스턴스 제공
    public static MailSender getInstance() {
        return INSTANCE;
    }

    // 초기화 메소드
    void init() {
        try {
            // [1] mail.properties 설정을 불러와, 아이디 비밀번호 적용
            props.load(getClass().getResourceAsStream("/mail.properties"));
            this.username = props.getProperty("mail.username");
            this.password = props.getProperty("mail.password");

            // [2] 메일 전송을 위한 정보 삽입
            props.put("mail.smtp.host", "smtp.gmail.com");  // Gmail SMTP
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

        } catch (Exception e) {
            System.out.printf("MailSender 초기화를 위한 mail.properties 읽기에 실패했습니다! 원인 : %s\n", e);
            throw new MailException(e);
        }
    }


    /**
     * 메일 전송
     * @param to        전송 대상 이메일 주소
     * @param subject   메일 제목
     * @param text      메일 본문
     */
    public void sendMail(String to, String subject, String text) {

        try {
            // [1] 메일 전송을 위한 Session 객체 조회
            Session session = getMailSession();

            // [2] 메일 내용을 담을 Message 객체 생성
            Message message = createMessage(to, subject, session);

            // [3] 본문
            MimeBodyPart htmlPart = createHtmlBodyPart(text);   // HTML part
            File logoImage = FileUtils.getStoredFile(FileUtils.DIR_BASE, "logo1.png");    // 이미지 파일
            MimeBodyPart imagePart = createImageBodyPart(logoImage, "logoImage");       // 이미지 part

            // [4] 본문 내용 조립 후 전송
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);   // 본문
            multipart.addBodyPart(imagePart);  // 이미지
            message.setContent(multipart);
            Transport.send(message);
            System.out.printf("메일 전송 완료! 전송 대상 = %s\n", to);

        } catch (MessagingException | IOException e) {
            System.out.printf("메세지 발송 실패! 전송 대상 : %s, 원인 : %s\n", to, e);
            throw new MailException(e);
        }
    }

    // jakarta.mail 라이브러리의 이메일 전송을 위한 세션 객체 생성 (HTTP Session 과는 다른 개념)
    private Session getMailSession() {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    
    // 메세지를 보내기 위한 Message 객체 생성
    private Message createMessage(String to, String subject, Session session) throws MessagingException, UnsupportedEncodingException {

        // 메세지 객체 생성
        Message message = new MimeMessage(session);

        // 메세지 내 발신자, 수신자, 제목을 설정 후 반환
        message.setFrom(new InternetAddress(username, "Studyroad")); // 발신자
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 수신자
        message.setSubject(subject); // 메일 제목
        return message;
    }


    // 메세지 본문 메세지를 담은 객체 생성
    private MimeBodyPart createHtmlBodyPart(String text) throws MessagingException {

        // 예시 텍스트
        String htmlText = new StringBuilder()
        		.append("<img src='cid:logoImage' style='width:300px; height:auto;'/><br>")
                .append("<h2>비밀번호 재설정 안내</h2><br>")
                .append(text)
                .toString();

        // 보내는 타입을 "text/html" 으로 설정 후 반환
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlText, "text/html; charset=UTF-8");
        return htmlPart;
    }


    // 메세지 본문 내 보일 이미지를 담은 객체 생성
    private MimeBodyPart createImageBodyPart(File file, String cidName) throws IOException, MessagingException {
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile(file);
        imagePart.setHeader("Content-ID", String.format("<%s>", cidName)); // 상단의 "cid" 이름
        imagePart.setDisposition(MimeBodyPart.INLINE);  // 첨부 파일이 아닌, 본문에 포함
        return imagePart;
    }


}
