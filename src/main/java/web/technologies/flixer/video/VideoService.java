package web.technologies.flixer.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public List<Video> getVideos(){
        return videoRepository.findAll();
    }

    public void addNewVideo(Video video) {
        videoRepository.save(video);
    }
}
