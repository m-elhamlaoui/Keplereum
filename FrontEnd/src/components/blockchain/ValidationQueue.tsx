import { useState } from 'react';
import BlockchainWindow from './BlockchainWindow';
import { useBlockchainContext } from '@/contexts/BlockchainContext';
import { Satellite, CircleDot, CheckCircle2, Users } from 'lucide-react';
import { KeyRound } from 'lucide-react';

const ValidationQueue = () => {
  const { validators } = useBlockchainContext();
  const activeValidators = validators.filter(v => v.isActive);

  // State to store the hovered validator's address and position
  const [hoveredValidator, setHoveredValidator] = useState<{
    address: string;
    x: number;
    y: number;
  } | null>(null);

  const currentValidatorIndex = 0;

  const handleMouseEnter = (validator: string, event: React.MouseEvent) => {
    const rect = (event.target as HTMLElement).getBoundingClientRect();
    setHoveredValidator({
      address: validator,
      x: rect.left + rect.width / 2,
      y: rect.top - 10,
    });
  };

  const handleMouseLeave = () => {
    setHoveredValidator(null);
  };

  return (
    <BlockchainWindow title="AUTHORITY / VALIDATION QUEUE" className="h-full">
      <div className="p-3 h-full flex">
        {/* Left section - Total validators count */}
        <div className="w-1/4 bg-satellite-dark rounded-lg mr-3 flex flex-col items-center justify-center">
          <Users className="h-12 w-12 text-blue-400 mb-3" />
          <div className="text-2xl font-bold text-white">{activeValidators.length}</div>
          <div className="text-sm text-gray-400 text-center">
            <span className="font-bold">Active Validators</span>
          </div>
        </div>

        {/* Right section - Validators list with horizontal scroll */}
        <div className="w-3/4 overflow-x-auto custom-scrollbar flex items-center h-full">
          <div className="flex space-x-4 h-full">
            {activeValidators.map((validator, index) => {
              const isCurrentValidator = index === currentValidatorIndex;
              const isNextValidator = index === (currentValidatorIndex + 1) % activeValidators.length;

              return (
                <div
                  key={validator.address}
                  className={`flex-shrink-0 relative flex flex-col items-center justify-center rounded-full aspect-square h-full max-h-full transition-all duration-300 ${
                    isCurrentValidator
                      ? 'bg-gradient-to-r from-green-800 to-green-700'
                      : isNextValidator
                      ? 'bg-gradient-to-r from-blue-800 to-blue-700'
                      : 'bg-satellite-dark'
                  }`}
                  onMouseEnter={(e) => handleMouseEnter(validator.address, e)}
                  onMouseLeave={handleMouseLeave}
                >
                  <div className="absolute top-2 right-2">
                    {isCurrentValidator ? (
                      <CheckCircle2 className="h-4 w-4 text-green-400" />
                    ) : isNextValidator ? (
                      <CircleDot className="h-4 w-4 text-blue-400" />
                    ) : (
                      <CircleDot className="h-4 w-4 text-gray-400" />
                    )}
                  </div>

                  <Satellite
                    className={`mb-2 h-8 w-8 ${
                      isCurrentValidator
                        ? 'text-green-400'
                        : isNextValidator
                        ? 'text-blue-400'
                        : 'text-gray-400'
                    }`}
                  />

                  <div className={`text-xs font-extrabold text-center truncate w-3/4 ${
                      isCurrentValidator
                        ? 'text-green-400'
                        : isNextValidator
                        ? 'text-blue-300'
                        : 'text-gray-400'
                    }`}>
                    {`${validator.name}`}
                  </div>

                    <div className={`text-s text-center truncate w-3/4 font-bold ${
                      isCurrentValidator
                      ? 'text-green-500'
                      : isNextValidator
                      ? 'text-blue-400'
                      : 'text-gray-300'
                    }`}>
                    <KeyRound className="inline-block mr-1 mb-1" size={20} />
                    {`${validator.address.slice(0, 15)}...}`}
                    </div>

                  <div className="text-xs text-gray-400 text-center">
                    {`${validator.blocksValidated} blocks`}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>

      {/* Display hovered validator's address as a tooltip */}
      {hoveredValidator && (
        <div
          className="absolute bg-gray-800 text-white text-xs p-2 rounded shadow-lg"
          style={{
            left: hoveredValidator.x,
            top: hoveredValidator.y,
            transform: 'translate(-50%, -100%)',
            pointerEvents: 'none',
          }}
        >
          {hoveredValidator.address}
        </div>
      )}
    </BlockchainWindow>
  );
};

export default ValidationQueue;