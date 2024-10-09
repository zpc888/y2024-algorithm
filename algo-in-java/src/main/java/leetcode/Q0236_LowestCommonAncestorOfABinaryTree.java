package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

public class Q0236_LowestCommonAncestorOfABinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//		return findLCA_V1(root, p, q).lca;

        findLCA_V2(root, p, q);
        return lcaV2;

//        try {
//            findLCA_V3_ThrowRuntimeExToShortCircuit_Causing_Slower(root, p, q);
//            return lcaV2;
//        } catch (RuntimeException ignored) {
//            return lcaV2;
//        }
    }

    private TreeNode lcaV2 = null;

    @MetricsRuntime(ms = 9, beats = 15.11)
    @MetricsMemory( mb = 44.62, beats = 60.16)
    private int findLCA_V3_ThrowRuntimeExToShortCircuit_Causing_Slower(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return 0;
        }
        int left = findLCA_V3_ThrowRuntimeExToShortCircuit_Causing_Slower(node.left, p, q);
        int right = findLCA_V3_ThrowRuntimeExToShortCircuit_Causing_Slower(node.right, p, q);
        int mid = (node == p || node == q) ? 1 : 0;
        if (mid + left + right >= 2 && lcaV2 == null) {
            lcaV2 = node;
            throw new RuntimeException("Found, short-circuit");
        }
        return mid + left + right;
    }

    @MetricsRuntime(ms = 6, beats = 100)
    @MetricsMemory( mb = 44.69, beats = 60.16)
    private int findLCA_V2(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return 0;
        }
        int left = findLCA_V2(node.left, p, q);
        int right = findLCA_V2(node.right, p, q);
        int mid = (node == p || node == q) ? 1 : 0;
        if (mid + left + right >= 2 && lcaV2 == null) {
            lcaV2 = node;
        }
        return mid + left + right;
    }

    @MetricsRuntime(ms = 7, beats = 62.26)
    @MetricsMemory( mb = 44.80, beats = 44.78)
	private Result findLCA_V1(TreeNode node, TreeNode p, TreeNode q) {
		if (node == null) {
			return new Result(false, false, null);
		}
		Result leftRes  = findLCA_V1(node.left, p, q);
		Result rightRes = findLCA_V1(node.right, p, q);
		boolean hasP = leftRes.hasP || rightRes.hasP || node == p;
		boolean hasQ = leftRes.hasQ || rightRes.hasQ || node == q;
		TreeNode lca = leftRes.lca != null ? leftRes.lca
			: (rightRes.lca != null ? rightRes.lca 
					: (hasP && hasQ ? node : null)
			  );
		return new Result(hasP, hasQ, lca);
	}

	public static class Result {
		public boolean hasP;
		public boolean hasQ;
		public TreeNode lca;

		public Result(boolean hasP, boolean hasQ, TreeNode lca) {
			this.hasP = hasP;
			this.hasQ = hasQ;
			this.lca  = lca;
		}
	}
}
