package com.makeuplab.service.impl;


import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.makeuplab.model.Course;
import com.makeuplab.model.User;
import com.makeuplab.service.CourseService;
import com.makeuplab.service.PDFExporterService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class PDFExporterServiceImpl implements PDFExporterService {
    private final CourseService courseService;

    public PDFExporterServiceImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String convertCyrilic(String message) {
        char[] abcCyr =   {' ','а','б','в','г','д','ѓ','е', 'ж','з','ѕ','и','ј','к','л','љ','м','н','њ','о','п','р','с','т', 'ќ','у', 'ф','х','ц','ч','џ','ш', 'А','Б','В','Г','Д','Ѓ','Е', 'Ж','З','Ѕ','И','Ј','К','Л','Љ','М','Н','Њ','О','П','Р','С','Т', 'Ќ', 'У','Ф', 'Х','Ц','Ч','Џ','Ш','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','/','-'};
        String[] abcLat = {" ","a","b","v","g","d","]","e","zh","z","y","i","j","k","l","q","m","n","w","o","p","r","s","t","'","u","f","h", "c",";", "x","{","A","B","V","G","D","}","E","Zh","Z","Y","I","J","K","L","Q","M","N","W","O","P","R","S","T","KJ","U","F","H", "C",":", "X","{", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","/","-"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void export(HttpServletResponse response, User user, Long courseId) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());


        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(20);
        font.setColor(Color.BLACK);

        Paragraph heading = new Paragraph("CERTIFICATE of COMPLETION", font);
        heading.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(heading);
        Paragraph blank = new Paragraph(" ");
        document.add(blank);

        Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
        font1.setSize(16);
        font1.setColor(Color.BLACK);

        Paragraph client = new Paragraph("This certificate is hereby granted to: \n " + user.getFirst_name()+" "  + user.getLast_name(), font1);
        document.add(client);
        document.add(blank);

        Course course = this.courseService.findById(courseId);
        Paragraph courseDescription = new Paragraph("For successfully completing the course: " + course.getTitle(), font1);
        document.add(courseDescription);



        Paragraph by = new Paragraph("Issued by: MakeupLab Learning System", font1);
        document.add(by);

        Paragraph date = new Paragraph("On date: " + LocalDate.now(), font1);
        document.add(date);


        document.close();

    }
}
