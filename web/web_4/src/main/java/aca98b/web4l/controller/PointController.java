package aca98b.web4l.controller;

import aca98b.web4l.model.request.PointRequest;
import aca98b.web4l.model.response.PointResponse;
import aca98b.web4l.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    @PostMapping("/add")
    public ResponseEntity<PointResponse> createPoint(@RequestBody PointRequest request) {
        return ResponseEntity.ok(pointService.create(request));
    }

    @GetMapping("/list")
    public ResponseEntity<PointResponse> getElements(){
        return ResponseEntity.ok(pointService.list());
    }

    @PostMapping("/clear")
    public ResponseEntity<PointResponse> deleteAll() {
        return ResponseEntity.ok(pointService.clearTable());
    }
}
