import React, { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { useSatelliteContext } from '@/contexts/SatelliteContext';
import { earthRadiusSceneUnits } from '@/utils/constants';

// Equirectangular projection: maps lat/lon to 2D canvas
function project3DTo2D(position: THREE.Vector3, width: number, height: number) {
  const radius = position.length();
  const lat = Math.asin(position.y / radius) * (180 / Math.PI);
  const lon = Math.atan2(position.z, -position.x) * (180 / Math.PI);
  const x = ((lon + 180) / 360) * width;
  const y = ((90 - lat) / 180) * height;
  return { x, y, lat, lon };
}

const Earth2DViewer = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const animationIdRef = useRef<number>();
  const { satellites, selectedSatellite } = useSatelliteContext();

  // Get current interpolated position for a satellite (same as 3D viewer)
  function getCurrentSatellitePosition(satellite: any, currentTime: number) {
    if (!satellite.orbitalData?.points3D || !satellite.orbitalData?.periodSeconds) return null;
    const speedMultiplier = satellite.speedMultiplier ?? 1;
    const adjustedTime = (currentTime * speedMultiplier) % satellite.orbitalData.periodSeconds;
    const points = satellite.orbitalData.points3D;
    const period = satellite.orbitalData.periodSeconds;
    const normalizedTime = adjustedTime / period;
    const idx = Math.floor(normalizedTime * (points.length - 1));
    const nextIdx = (idx + 1) % points.length;
    const frac = (normalizedTime * (points.length - 1)) - idx;
    const pos = new THREE.Vector3();
    pos.lerpVectors(points[idx], points[nextIdx], frac);
    return pos;
  }

  // Draw the Earth background (simple blue fill)
  function drawEarth(ctx: CanvasRenderingContext2D, width: number, height: number) {
    ctx.fillStyle = '#0B4D73';
    ctx.fillRect(0, 0, width, height);
    // Optionally: draw grid lines or continents here
  }

  // Draw a satellite's trajectory
  function drawTrajectory(ctx: CanvasRenderingContext2D, satellite: any, width: number, height: number) {
    if (!satellite.orbitalData?.points3D) return;
    ctx.save();
    ctx.strokeStyle = satellite.color;
    ctx.lineWidth = 2;
    ctx.globalAlpha = 0.7;
    ctx.beginPath();
    satellite.orbitalData.points3D.forEach((point: THREE.Vector3, i: number) => {
      const { x, y } = project3DTo2D(point, width, height);
      if (i === 0) ctx.moveTo(x, y);
      else ctx.lineTo(x, y);
    });
    ctx.stroke();
    ctx.restore();
  }

  // Draw the current satellite position
  function drawSatellite(ctx: CanvasRenderingContext2D, satellite: any, width: number, height: number, currentTime: number) {
    const pos = getCurrentSatellitePosition(satellite, currentTime);
    if (!pos) return;
    const { x, y } = project3DTo2D(pos, width, height);
    ctx.save();
    ctx.fillStyle = satellite.color;
    ctx.beginPath();
    ctx.arc(x, y, 6, 0, 2 * Math.PI);
    ctx.fill();
    // Highlight selected satellite
    if (selectedSatellite && satellite.id === selectedSatellite.id) {
      ctx.strokeStyle = '#fff';
      ctx.lineWidth = 3;
      ctx.beginPath();
      ctx.arc(x, y, 12, 0, 2 * Math.PI);
      ctx.stroke();
    }
    // Draw name
    ctx.fillStyle = '#fff';
    ctx.font = '12px Arial';
    ctx.textAlign = 'left';
    ctx.fillText(satellite.name, x + 15, y - 10);
    ctx.restore();
  }

  // Animation loop
  function animate() {
    if (!canvasRef.current) return;
    const canvas = canvasRef.current;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;
    const width = canvas.width;
    const height = canvas.height;
    ctx.clearRect(0, 0, width, height);
    drawEarth(ctx, width, height);
    const now = Date.now() / 1000;
    satellites.forEach(sat => {
      drawTrajectory(ctx, sat, width, height);
      drawSatellite(ctx, sat, width, height, now);
    });
    animationIdRef.current = requestAnimationFrame(animate);
  }

  // Handle resizing
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const resize = () => {
      const rect = canvas.getBoundingClientRect();
      canvas.width = rect.width;
      canvas.height = rect.height;
    };
    resize();
    window.addEventListener('resize', resize);
    return () => window.removeEventListener('resize', resize);
  }, []);

  // Start animation
  useEffect(() => {
    animate();
    return () => {
      if (animationIdRef.current) cancelAnimationFrame(animationIdRef.current);
    };
  }, [satellites]);

  return (
    <canvas
      ref={canvasRef}
      className="w-full h-full"
      style={{ background: '#0B4D73' }}
    />
  );
};

export default Earth2DViewer;
