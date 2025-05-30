package com.example.jeeHamlaoui.Sattelites_Service.service;

import com.example.jeeHamlaoui.Sattelites_Service.exceptions.ResourceNotFoundException;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteTrajectory;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.OrbitalTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.Point3D;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteTrajectoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SatelliteTrajectoryService {

    private final SatelliteTrajectoryRepository satelliteTrajectoryRepository;
    private final SatelliteService satelliteService;

    @Autowired
    public SatelliteTrajectoryService(SatelliteTrajectoryRepository satelliteTrajectoryRepository, SatelliteService satelliteService) {
        this.satelliteTrajectoryRepository = satelliteTrajectoryRepository;
        this.satelliteService = satelliteService;
    }

    public SatelliteTrajectory saveTrajectory(SatelliteTrajectory satelliteTrajectory) {
        return satelliteTrajectoryRepository.save(satelliteTrajectory);
    }

    public List<SatelliteTrajectory> getAllTrajectories(Long id) {
        Optional<Satellite> satelliteOpt = satelliteService.findById(id);
        if (satelliteOpt.isPresent()) {
            Satellite satellite = satelliteOpt.get();
        }
        return satelliteTrajectoryRepository.findAllBySatellite_Id(id);
    }

    public SatelliteTrajectory getCurrentTrajectoryById(Long satelliteId) {
        // Verify satellite exists first
        Satellite satellite = satelliteService.findById(satelliteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Satellite not found with id: " + satelliteId
                ));

        // Get most recent trajectory
        return satelliteTrajectoryRepository
                .findTopBySatelliteOrderByStartTimeDesc(satellite)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No trajectories found for satellite with id: " + satelliteId
                ));
    }

        public SatelliteTrajectory updateTrajectory(Long id, SatelliteTrajectory updatedTrajectory) {
        return satelliteTrajectoryRepository.findById(id)
                .map(existingTrajectory -> {
                    existingTrajectory.setStatus(updatedTrajectory.getStatus());
                    existingTrajectory.setStartTime(updatedTrajectory.getStartTime());
                    existingTrajectory.setEndTime(updatedTrajectory.getEndTime());
                    existingTrajectory.setOrbitEccentricity(updatedTrajectory.getOrbitEccentricity());
                    existingTrajectory.setOrbitInclination(updatedTrajectory.getOrbitInclination());
                    existingTrajectory.setOrbitRightAscension(updatedTrajectory.getOrbitRightAscension());
                    existingTrajectory.setOrbitArgumentOfPerigee(updatedTrajectory.getOrbitArgumentOfPerigee());
                    existingTrajectory.setOrbitMeanAnomaly(updatedTrajectory.getOrbitMeanAnomaly());
                    existingTrajectory.setOrbitPeriapsis(updatedTrajectory.getOrbitPeriapsis());
                    existingTrajectory.setChangeReason(updatedTrajectory.getChangeReason());
                    existingTrajectory.setSatellite(updatedTrajectory.getSatellite());
                    return satelliteTrajectoryRepository.save(existingTrajectory);
                })
                .orElseThrow(() -> new RuntimeException("Trajectory not found with id " + id));
    }

    public void deleteTrajectory(Long id) {
        satelliteTrajectoryRepository.deleteById(id);
    }

    public OrbitalTrajectoryResponse calculateOrbitalTrajectory(
            double perigeeAltitude,
            double eccentricity,
            double inclination,
            double longitudeOfAscendingNode,
            double argumentOfPeriapsis,
            double earthRadius,
            double scaleFactor
    ) {
        double perigeeRadius = earthRadius + perigeeAltitude;
        double semiMajorAxis = perigeeRadius / (1 - eccentricity);
        double semiMajorAxisMeters = semiMajorAxis * 1000;
        double GM = 3.986004418e14;
        double periodSeconds = 2 * Math.PI * Math.sqrt(Math.pow(semiMajorAxisMeters, 3) / GM);

        int numSamples = 500;
        double timeStepSeconds = periodSeconds / numSamples;

        List<Double> cartesianValues = new ArrayList<>();
        List<Point3D> points3D = new ArrayList<>();

        for (int i = 0; i <= numSamples; i++) {
            double sampleTime = i * timeStepSeconds;
            double meanAnomaly = (i / (double) numSamples) * 2 * Math.PI;

            // Kepler's equation numerical solving for eccentric anomaly (E)
            double eccentricAnomaly = meanAnomaly;
            for (int j = 0; j < 50; j++) {
                double nextEccentricAnomaly = meanAnomaly + eccentricity * Math.sin(eccentricAnomaly);
                if (Math.abs(nextEccentricAnomaly - eccentricAnomaly) < 1e-6) break;
                eccentricAnomaly = nextEccentricAnomaly;
            }

            double trueAnomaly = 2 * Math.atan2(
                    Math.sqrt(1 + eccentricity) * Math.sin(eccentricAnomaly / 2),
                    Math.sqrt(1 - eccentricity) * Math.cos(eccentricAnomaly / 2)
            );
            double radius = semiMajorAxisMeters * (1 - eccentricity * Math.cos(eccentricAnomaly));

            double xOrbital = radius * Math.cos(trueAnomaly);
            double yOrbital = radius * Math.sin(trueAnomaly);

            double inclinationRad = Math.toRadians(inclination);
            double lonAscNodeRad = Math.toRadians(longitudeOfAscendingNode);
            double argPeriapsisRad = Math.toRadians(argumentOfPeriapsis);

            double xTemp = xOrbital * Math.cos(argPeriapsisRad) - yOrbital * Math.sin(argPeriapsisRad);
            double yTemp = xOrbital * Math.sin(argPeriapsisRad) + yOrbital * Math.cos(argPeriapsisRad);

            double yFinal = yTemp * Math.cos(inclinationRad);
            double zFinal = yTemp * Math.sin(inclinationRad);

            double xFinal = xTemp * Math.cos(lonAscNodeRad) - yFinal * Math.sin(lonAscNodeRad);
            double yFinalRotated = xTemp * Math.sin(lonAscNodeRad) + yFinal * Math.cos(lonAscNodeRad);

            // Scale for visualization
            double xScene = xFinal * scaleFactor / 1000;
            double yScene = yFinalRotated * scaleFactor / 1000;
            double zScene = zFinal * scaleFactor / 1000;

            cartesianValues.add(sampleTime);
            cartesianValues.add(xScene);
            cartesianValues.add(yScene);
            cartesianValues.add(zScene);

            points3D.add(new Point3D(xScene, yScene, zScene));
        }

        return new OrbitalTrajectoryResponse(cartesianValues, points3D, periodSeconds);
    }

    public OrbitalTrajectoryResponse calculateOrbitalTrajectoryBySatelliteId(Long id,double earthRadius,double scaleFactor) {
        Optional<Satellite> satelliteOpt = satelliteService.findById(id);
        if (satelliteOpt.isPresent()) {
            Satellite satellite = satelliteOpt.get();

            // Get the latest trajectory for this satellite
            Optional<SatelliteTrajectory> latestTrajectoryOpt = satelliteTrajectoryRepository.findTopBySatelliteOrderByStartTimeDesc(satellite);

            if (latestTrajectoryOpt.isPresent()) {
                SatelliteTrajectory latestTrajectory = latestTrajectoryOpt.get();

                return calculateOrbitalTrajectory(
                        latestTrajectory.getOrbitArgumentOfPerigee(),
                        latestTrajectory.getOrbitEccentricity(),
                        latestTrajectory.getOrbitInclination(),
                        latestTrajectory.getOrbitRightAscension(),
                        latestTrajectory.getOrbitPeriapsis(),
                          earthRadius,
                          scaleFactor

                          // Assuming this is stored in the satellite entity
                );
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public SatelliteTrajectoryResponse createTrajectory(
            SatelliteTrajectoryRequest request
    ) {
        // 1. Fetch Satellite (or throw 404)
        Satellite satellite = satelliteService.findById(request.getSatelliteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Satellite not found with ID: " + request.getSatelliteId()
                ));
        SatelliteTrajectory lastTrajectory = satelliteTrajectoryRepository.findTopBySatelliteOrderByEndTimeDesc(satellite);
        SatelliteTrajectory trajectory = new SatelliteTrajectory();

        trajectory.setStartTime(Instant.now());

        if (lastTrajectory != null) {
            lastTrajectory.setEndTime(trajectory.getStartTime());
            satelliteTrajectoryRepository.save(lastTrajectory);
        }

        // 2. Map Request to Entity

        trajectory.setStatus(request.getStatus());
        trajectory.setOrbitEccentricity(request.getOrbitEccentricity());
        trajectory.setOrbitInclination(request.getOrbitInclination());
        trajectory.setOrbitRightAscension(request.getOrbitRightAscension());
        trajectory.setOrbitArgumentOfPerigee(request.getOrbitArgumentOfPerigee());
        trajectory.setOrbitMeanAnomaly(request.getOrbitMeanAnomaly());
        trajectory.setOrbitPeriapsis(request.getOrbitPeriapsis());
        trajectory.setChangeReason(request.getChangeReason());
        trajectory.setSatellite(satellite);

        // 3. Save and return DTO
        SatelliteTrajectory savedTrajectory = satelliteTrajectoryRepository.save(trajectory);
        return new SatelliteTrajectoryResponse(savedTrajectory);
    }
}
