package cemc.s3;

import static org.junit.Assert.*;

import org.junit.Test;

public class NeedleSearchTest {

	@Test
	public void testGetTotalOcc_1_01() {
		String needle ="aab";
		String hays = "abacabaa";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 2);
		System.out.println(total);
	}
	
	@Test
	public void testGetTotalOcc() {
		String needle ="abc";
		String hays = "bcbac";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 2);
		System.out.println(total);
	}

	@Test
	public void testGetTotalOcc_1_02() {
		String needle ="xxxxxxxx";
		String hays = "nddyhsslitxxroowyofmboanirdoepueroerhrczfrpdijhxtsgzgtpzobsgricmgoqtftdctvusddsvsopztbdpbxgxcybpctclvhyuedorzrwmpzifvtnphrvwdwbovlexaxnwkgtodzlewplvurnjcedfisiapenjlhexogyeexxruupymvgsufmvpklhujnautur";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 0);
		System.out.println(total);
	}

	@Test
	public void testGetTotalOcc_1_03() {
		String needle ="gggggggg";
		String hays = "pilqbbvitbcnyzadvlqlsvdxagwxkvwwqoponfhiqbynwmsfontjjvlgegnfxgggggggggtnjmwbxzhwlggggggggndwvizzkwoxfmwqtmgbwbsrnqoxfrltkcvaerokapmzuhohaggggggggwucpotxkggggggggdmsoinzgdwyavvttixlvheixjzxmiqwvzopjvmo";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 1);
		System.out.println(total);
	}
	
	@Test
	public void testGetTotalOcc_2_04() {
		String needle ="ffxewmweexpmiipfmfgw";
		String hays = "fwfewpmegeiwfmipxmxffefppfemifxgmmewxwiwkmnerxcholtimemipxgewifewfmpfxfwnfemefmppxwfwiiefmwfxgtvwfiiwpegfexmwmxmffpeinpskbjazpvfwxwegieffmwxmpfmpeihrzx";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 7);
		System.out.println(total);
	}
	
	@Test
	public void testGetTotalOcc_1_05() {
		String needle ="ggggggll";
		String hays = "lglgggggvqglnuzjvtvitezoymuiytggggllggsamztqrfoslgggglggqtrsinggllggggmetbkhxhskglgggglglgetqwoaggglgggljjgisojggllgggggggllkrmilgggglgghgeajxqasaolgggglggdurlgggglggnqcggggllggvfmftggggggllbgwgidifqt";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 10);
		System.out.println(total);
	}
	
	@Test
	public void testGetTotalOcc_1_06() {
		String needle ="ggguuuug";
		String hays = "sygofjuguuggguguguuuggaxbcvutqwliguugguugetmdgguugugguguguguuugggguuuguggugcugguuugugguuguughlnnqnbgguguuugmuugguuggpuclvuugguuggnulhsuauggguuguzguugugguuggugugugugugugxbtffxbuggguugupuguugggugguguuug";
		int total  = NeedleSearchOld.getTotalOcc(needle, hays);
		assertEquals(total, 28);
		System.out.println(total);
	}

}
