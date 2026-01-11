const API_BASE_URL = 'http://localhost:8222/api/v1/users';

interface LoginCredentials {
  email: string;
  password: string;
}

export async function loginUser(credentials: LoginCredentials): Promise<void> {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      credentials: 'include', // This is needed if your backend sends cookies
      body: JSON.stringify({
        email: credentials.email, // The backend expects email instead of username
        password: credentials.password
      }),
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Login failed');
    }
  } catch (error) {
    if (error instanceof TypeError && error.message.includes('Failed to fetch')) {
      throw new Error('Unable to connect to the server. Please check if the backend is running.');
    }
    throw error;
  }
}

export async function logoutUser(): Promise<void> {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/logout`, {
      method: 'POST',
      credentials: 'include', // Important: needed to include cookies
    });

    if (!response.ok) {
      throw new Error('Logout failed');
    }
  } catch (error) {
    console.error('Logout error:', error);
    throw error;
  }
}