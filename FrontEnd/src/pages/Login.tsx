import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { loginUser } from '../utils/api';
import { Satellite, Loader2 } from 'lucide-react';
import * as THREE from 'three';

export function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  useEffect(() => {
    if (!containerRef.current) return;

    // Initialize Three.js scene
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer({ alpha: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    containerRef.current.appendChild(renderer.domElement);

    // Create stars
    const starsGeometry = new THREE.BufferGeometry();
    const starsMaterial = new THREE.PointsMaterial({
      color: 0xffffff,
      size: 0.3,
      transparent: true
    });

    const starsVertices = [];
    for (let i = 0; i < 15000; i++) {
      const x = THREE.MathUtils.randFloatSpread(2000);
      const y = THREE.MathUtils.randFloatSpread(2000);
      const z = THREE.MathUtils.randFloatSpread(2000);
      starsVertices.push(x, y, z);
    }

    starsGeometry.setAttribute('position', new THREE.Float32BufferAttribute(starsVertices, 3));
    const stars = new THREE.Points(starsGeometry, starsMaterial);
    scene.add(stars);

    camera.position.z = 5;

    // Animation
    const animate = () => {
      requestAnimationFrame(animate);
      stars.rotation.x += 0.0001;
      stars.rotation.y += 0.0001;
      renderer.render(scene, camera);
    };

    // Handle resize
    const handleResize = () => {
      const width = window.innerWidth;
      const height = window.innerHeight;
      camera.aspect = width / height;
      camera.updateProjectionMatrix();
      renderer.setSize(width, height);
    };

    window.addEventListener('resize', handleResize);
    animate();

    return () => {
      window.removeEventListener('resize', handleResize);
      if (containerRef.current?.contains(renderer.domElement)) {
        containerRef.current.removeChild(renderer.domElement);
      }
    };
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setIsLoading(true);

    try {
      await loginUser(formData);
      login();
      navigate('/');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Login failed');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-satellite-dark overflow-hidden relative">
      {/* THREE.js stars background */}
      <div ref={containerRef} className="absolute inset-0" />

      {/* Login container */}
      <div className="relative z-10 w-full max-w-md p-8">
        <div className="bg-satellite-dark-header/80 rounded-2xl border border-satellite-border shadow-2xl backdrop-blur-sm">
          <div className="p-8">
            {/* Logo and title */}
            <div className="flex flex-col items-center space-y-4 mb-8">
              <div className="relative">
                <Satellite className="w-16 h-16 text-satellite-accent" />
                <div className="absolute inset-0 animate-pulse bg-satellite-accent/20 rounded-full blur-xl"></div>
              </div>
              <h1 className="text-3xl font-bold text-white">Keplereum</h1>
              <p className="text-gray-400 text-center">
                Access your satellite control dashboard
              </p>
            </div>

            {/* Login form */}
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="space-y-4">
                <div>
                  <label htmlFor="email" className="block text-sm font-medium text-gray-300 mb-1">
                    Email
                  </label>
                  <input
                    id="email"
                    name="email"
                    type="email"
                    required
                    className="w-full px-4 py-3 rounded-lg bg-satellite-dark border border-satellite-border text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-satellite-accent focus:border-transparent transition-all"
                    placeholder="Enter your email"
                    value={formData.email}
                    onChange={handleChange}
                  />
                </div>
                <div>
                  <label htmlFor="password" className="block text-sm font-medium text-gray-300 mb-1">
                    Password
                  </label>
                  <input
                    id="password"
                    name="password"
                    type="password"
                    required
                    className="w-full px-4 py-3 rounded-lg bg-satellite-dark border border-satellite-border text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-satellite-accent focus:border-transparent transition-all"
                    placeholder="Enter your password"
                    value={formData.password}
                    onChange={handleChange}
                  />
                </div>
              </div>

              {error && (
                <div className="text-red-400 text-sm text-center bg-red-400/10 py-2 px-4 rounded-lg border border-red-400/20">
                  {error}
                </div>
              )}

              <button
                type="submit"
                disabled={isLoading}
                className="w-full py-3 px-4 rounded-lg bg-satellite-accent text-white font-medium hover:bg-satellite-accent/90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-satellite-accent focus:ring-offset-satellite-dark transition-all disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center space-x-2"
              >
                {isLoading ? (
                  <>
                    <Loader2 className="w-5 h-5 animate-spin" />
                    <span>Signing in...</span>
                  </>
                ) : (
                  <span>Sign in</span>
                )}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
} 