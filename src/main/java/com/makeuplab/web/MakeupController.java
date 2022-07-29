package com.makeuplab.web;

import com.lowagie.text.DocumentException;
import com.makeuplab.model.*;
import com.makeuplab.model.enumerations.Role;
import com.makeuplab.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MakeupController {

    private final CommentService commentService;
    private final ContentService contentService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final QuestionService questionService;
    private final QuizService quizService;
    private final UserService userService;
    private final PDFExporterService pdfExporterService;
    private final QuizScoreService quizScoreService;


    public MakeupController(CommentService commentService, ContentService contentService, CourseService courseService, LessonService lessonService, QuestionService questionService, QuizService quizService, UserService userService, PDFExporterService pdfExporterService, QuizScoreService quizScoreService) {
        this.commentService = commentService;
        this.contentService = contentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.questionService = questionService;
        this.quizService = quizService;
        this.userService = userService;
        this.pdfExporterService = pdfExporterService;
        this.quizScoreService = quizScoreService;
    }


    @GetMapping
    public String getHomePage()
    {
        //this.userService.add("Teodora","Spasovska","teodora.spasovska","ts", Role.ROLE_USER);
        return "index.html";
    }

    @GetMapping("/courses")
    public String getCourses(Model model)
    {
        List<Course> courseList=this.courseService.findAll();

        model.addAttribute("courses",courseList);

        return "courses.html";
    }
    @GetMapping("/course/{id}")
    public String getCourse(@PathVariable Long id,Model model,HttpServletRequest request)
    {
        Course course=this.courseService.findById(id);
        String username = request.getRemoteUser();
        User user = this.userService.findByUsername(username);
        List<Lesson> lessonList=course.getLessonList();
        QuizScore quizScore=this.quizScoreService.findQuizScoreByUserAndCourse(user,course);
        model.addAttribute("score",quizScore.getScore());
        model.addAttribute("course",course);
        model.addAttribute("lessons",lessonList);
        return "course.html";
    }

    @GetMapping("/lesson/{id}")
    public String getLesson(@PathVariable Long id,Model model)
    {
        Lesson lesson=this.lessonService.findById(id);
        List<Content> contentList=lesson.getContentList();

        List<Comment> comments=lesson.getComments();

        Course course=this.courseService.findByLesson(lesson);
        model.addAttribute("course",course);
        model.addAttribute("lesson",lesson);
        model.addAttribute("contents",contentList);
        model.addAttribute("comments",comments);
        return "lesson.html";
    }

    @PostMapping("/addcomment/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String comment, HttpServletRequest request)
    {
        String username = request.getRemoteUser();
        User user = this.userService.findByUsername(username);
        Comment comment1=new Comment(user,this.lessonService.findById(id),comment);
        this.commentService.add(user.getId(),id,comment);
        Lesson lesson=this.lessonService.addComment(id,comment1);
        return "redirect:/lesson/"+lesson.getId();

    }

    @GetMapping("/quiz/{id}")
    public String getQuiz(@PathVariable Long id,Model model)
    {

        Course course=this.courseService.findById(id);
        Quiz quiz=course.getQuiz();
        model.addAttribute("quiz",quiz);
        model.addAttribute("questions",quiz.getQuestions());

        model.addAttribute("course",course);
        return "quiz.html";
    }

    @GetMapping("/register")
    public String getRegisterPage()
    {
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String username,
                               @RequestParam String pw)
    {
        this.userService.add(name,surname,username,pw,Role.ROLE_USER);
        return "redirect:/login";
    }
    @PostMapping("/quizScore")
    public String getScore(@RequestParam List<Long> question,@RequestParam Long quizId,
                           Model model,HttpServletRequest request)
    {
        String[] questionList = request.getParameterValues("question");
        List<Long> sub = new ArrayList<>();
        for (int i = 0; i < questionList.length; i++) {
            sub.add(Long.parseLong(questionList[i]));
        }
        List<Question> questions=this.questionService.findAllByIds(sub);

        Quiz quiz=this.quizService.findById(quizId);
        List<Question> trueQuestions=quiz.getQuestions().stream().filter(q->q.getAnswer().equals("Yes")).collect(Collectors.toList());

        List<Question> wrongQuestions=quiz.getQuestions().stream().filter(q->q.getAnswer().equals("No")).collect(Collectors.toList());
        double sum=this.quizScoreService.calculateScore(trueQuestions,wrongQuestions,questions);
        model.addAttribute("score",sum);

        String username = request.getRemoteUser();
        User user = this.userService.findByUsername(username);
        this.userService.setQuiz(user.getId(),quiz);
        this.quizScoreService.add(user,quiz,sum);
        model.addAttribute("course",quiz.getCourse());
        return "quiz-score.html";

    }
    @PostMapping("/export/pdf/{id}")
    public void exportToPDF(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) throws DocumentException, IOException {


        String username = request.getRemoteUser();
        User user = this.userService.findByUsername(username);

            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=certificate_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);


            this.pdfExporterService.export(response, user, id);


    }
}
