package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.entity.Video;
import web.technologies.flixer.service.UserService;
import web.technologies.flixer.service.VideoService;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getVideos(){
        return videoService.getVideos();
    }

    @PostMapping()
    public ResponseEntity<String> addNewVideo(@RequestBody Video video) {
        videoService.addNewVideo(video);
        return new ResponseEntity<>("Video" + video.getTitle() + " ajouté avec succès", HttpStatus.CREATED);

    }
}
