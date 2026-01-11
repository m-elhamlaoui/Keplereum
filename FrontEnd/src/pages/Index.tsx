import InfoComponent from "@/components/InfoComponent";
import Earth2DWindow from "@/components/Earth2DWindow";
import ObjectList from "@/components/ObjectList";
import Earth3DWindow from "@/components/Earth3DWindow";
import TrackingTool1 from "@/components/TrackingTool1";
import TrackingTool2 from "@/components/TrackingTool2";
import { useSatelliteContext } from "@/contexts/SatelliteContext";

const Index = () => {
  const { loading } = useSatelliteContext();

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-satellite-dark">
        <h1 className="text-white text-xl">Loading Satellites...</h1>
      </div>
    );
  }

  return (
    <div className="overflow-hidden bg-satellite-dark h-full">
      <main className="p-2 h-full">
        <div className="grid grid-cols-8 grid-rows-2 gap-2 h-full">
          {/* Top Row */}
          <InfoComponent />
          <Earth2DWindow />
          <ObjectList />
          
          {/* Bottom Row */}
          <Earth3DWindow />
          <TrackingTool1 />
          <TrackingTool2 />
        </div>
      </main>
    </div>
  );
};

export default Index;
