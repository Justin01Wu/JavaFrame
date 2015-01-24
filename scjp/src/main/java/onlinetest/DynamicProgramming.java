package onlinetest;

/**
 * 一幢 100 层的大楼,给你两个鸡蛋. 如果在第 n 层扔下鸡蛋,鸡蛋不碎,那么从前 n-1 层扔鸡蛋都不碎.
 * 这两只鸡蛋一模一样,不碎的话可以扔无数次. 已知鸡蛋在0层扔不会碎.
 * 提出一个策略, 要保证能测出鸡蛋恰好会碎的楼层, 并使此策略在最坏情况下所扔次数最少.
 *
 */
public class DynamicProgramming {
	
	/**
	 *  this can get result, but it is bad on performance because it is O(N3)
	 */
	private static int getMinSteps(int layer, int eggs){
		if(layer ==0){
			return 0;
		}
		if(eggs == 1){
			return layer;
		}
		
		
		int minSteps = Integer.MAX_VALUE;
		
		for (int i = 1; i<= layer + 1; i++){  // search all layer
			int thisLeftStep = getMinSteps(i-1, eggs-1) ;     // the current egg brake, so handle the rest
			int thisRightStep = getMinSteps(layer-i, eggs) ;  // the current egg didn't break; keep try it again
			if(thisRightStep > thisLeftStep){
				if(minSteps > thisRightStep){
					minSteps = thisRightStep;
				}
			}else{
				if(minSteps > thisLeftStep){
					minSteps = thisLeftStep;
				}
			}
			
		}
		return minSteps +1;
		
	}
	
	/**
	 * this is much better because it performance is O(Nlog2N)
	 */
	private static int getMaxLayers(int tryTimes, int eggs){
		if(tryTimes ==0){
			return 1;
		}
		if(eggs == 1){
			return tryTimes;
		}
		
		int left = getMaxLayers(tryTimes-1,eggs-1);  // the current egg brake, so handle the rest
		int right = getMaxLayers(tryTimes-1, eggs);  // the current egg didn't break; keep try it again
		return left + right + 1;
		
	}
	
	private static int getMinSteps2(int layer, int eggs){
		
		for(int tryTimes = 1; tryTimes <layer; tryTimes ++){
			
			int answer = getMaxLayers(tryTimes, eggs);
				if(answer >= layer){
					return tryTimes;
				}
		}
		return -1;
	}
	
	
	
	public static void main(String[] args) {
		int layer = 200;
		int eggs = 2;
		
//		int answer = getMinSteps(layer, eggs);
//		System.out.println(answer);
		
		int answer = getMinSteps2(layer, eggs);
		System.out.println(answer);
	}

}
