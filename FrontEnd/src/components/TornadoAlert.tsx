
import React from 'react';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';
import { Tornado } from 'lucide-react';

interface TornadoAlertProps {
  name: string;
  lat: number;
  lng: number;
  severity?: 'warning' | 'watch';
}

const TornadoAlert: React.FC<TornadoAlertProps> = ({ 
  name, 
  lat, 
  lng, 
  severity = 'warning' 
}) => {
  return (
    <Alert className={`mb-2 border-l-4 ${
      severity === 'warning' 
        ? 'border-l-red-500 bg-red-50/10 border-red-200' 
        : 'border-l-orange-500 bg-orange-50/10 border-orange-200'
    }`}>
      <Tornado className={`h-4 w-4 ${
        severity === 'warning' ? 'text-red-500' : 'text-orange-500'
      }`} />
      <AlertTitle className={`${
        severity === 'warning' ? 'text-red-700' : 'text-orange-700'
      }`}>
        Tornado {severity === 'warning' ? 'Warning' : 'Watch'}
      </AlertTitle>
      <AlertDescription className="text-sm text-gray-600">
        <div className="font-semibold">{name}</div>
        <div className="text-xs mt-1">
          Coordinates: {lat.toFixed(4)}°, {lng.toFixed(4)}°
        </div>
        <div className="text-xs mt-1 font-medium">
          {severity === 'warning' 
            ? 'Active tornado detected - Take immediate shelter' 
            : 'Conditions favorable for tornado development'
          }
        </div>
      </AlertDescription>
    </Alert>
  );
};

export default TornadoAlert;
