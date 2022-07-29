package com.makeuplab.service;

import com.lowagie.text.DocumentException;
import com.makeuplab.model.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface PDFExporterService {
    public String convertCyrilic(String message);

    public void export(HttpServletResponse response, User user,Long courseId)throws DocumentException, IOException;
}
