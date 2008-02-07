package test.org.tigris.mbt.io;

import org.tigris.mbt.Keywords;
import org.tigris.mbt.ModelBasedTesting;

import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.graph.impl.SparseGraph;

import junit.framework.TestCase;

public class GraphMLTest extends TestCase {

    // Test various types of efsm labels
    public void testMergeEFSM_Lables()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/efsm_lables/testMergeEFSM_Lables.graphml" );
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
			fail( e.getMessage() );
		}
    }
    
    // Test merging and verifying that unique id's are created
    public void testVerifyIds()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test23" );
	    	verifyIds( mbt.getGraph() );
		}
		catch ( Exception e)
		{
    		System.err.println(e.getMessage());
			fail( e.getMessage() );
		}
    }
    
    // Verify that mbt checks that subgraphs are unique.
    public void testUniqueSubGraphs()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test09" );
	    	fail( "Missing error message" );
    	}
    	catch ( RuntimeException e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Found 2 subgraphs using the same name: 'B', they are defined in files: '.*graphml.test09.(C|B).graphml', and :'.*graphml.test09.(B|C).graphml'" ) );
    	}
    }

    // Verify that mbt reports and exits when an node without name is found 
    public void testEmptyVertexLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test10" );
	    	fail( "Missing error message" );
    	}
    	catch ( RuntimeException e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Vertex is missing its label in file: '.*test10.B\\.graphml'" ) );
    	}
    }

    // Verify that mbt reports and exits when a recursive subgraph situation emerges 
    public void testRecuresiveIncident()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test11" );
	    	fail( "Missing error message" );
    	}
    	catch ( RuntimeException e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Found a subgraph containing a duplicate vertex with name: 'C', in file: '.*test11.C\\.graphml'" ) );
    	}
    }
    

    // Verify that mbt reports and exits when an edge containing a whitespace (tab) is found 
    public void testWhiteSpaceInEdgeLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test13" );
    	}
    	catch ( RuntimeException e)
    	{
    		System.err.println(e.getMessage());
			fail( e.getMessage() );
    	}
    }
    
    // Verify that mbt reports and exits when a vertex containing a whitespace (tab) is found 
    public void testWhiteSpaceInVertexLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test14" );
	    	fail( "Missing error message" );
    	}
    	catch ( RuntimeException e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Vertex has a label 'Containing a whitespace', containing whitespaces in file: '.*graphml.test14.C\\.graphml'" ) );
    	}
    }
    
    // Verify that a single graphml file with an edge coming from the START vertex with an empty label is catched.
    public void testMainGraphWithStartVertexOutEdgeEmptyLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test15" );
	    	fail( "Missing error message" );
    	}
    	catch ( Exception e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Did not find a Start vertex with an out edge with a label." ) );
    	}
    }
    
    // Verify that a file with 2 Start vertices is catched. Both Start vertices has edges with labels.
    public void testTwoStartVerticesWithLabels()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test17/test17.graphml" );
	    	fail( "Missing error message" );
    	}
    	catch ( Exception e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Only one Start vertex can exist in one file, see file 'graphml/test17/test17.graphml'" ) );
    	}
    }

    // Verify that a file with 2 Start vertices is catched. One edge has a label, the other has not.
    public void testTwoStartVerticesOneLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test18/test18.graphml" );
	    	fail( "Missing error message" );
    	}
    	catch ( Exception e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Only one Start vertex can exist in one file, see file 'graphml/test18/test18.graphml'" ) );
    	}
    }

    // Verify that a file with 2 Start vertices is catched. Both edges has no labels.
    public void testTwoStartVerticesNoLabel()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test19/test19.graphml" );
	    	fail( "Missing error message" );
    	}
    	catch ( Exception e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "Only one Start vertex can exist in one file, see file 'graphml/test19/test19.graphml'" ) );
    	}
    }

    // Verify that a file with 2 out edges from the Start vertex is catched.
    public void testTwoOutEdgesFromStart()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test20/test20.graphml" );
	    	fail( "Missing error message" );
    	}
    	catch ( Exception e)
    	{
    		String msg = e.getMessage();
    		assertTrue( msg.matches( "A Start vertex can only have one out edge, look in file: graphml/test20/test20.graphml" ) );
    	}
    }

    // Verify that merging of subgraphs works for graphml/mergeSubgraphs_01.
    public void test_mergeSubgraphs_01()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/mergeSubgraphs_01" );
	    	assertTrue( mbt.getGraph().getVertices().size() == 6 );
	    	assertTrue( mbt.getGraph().getEdges().size() == 16 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
    		fail( e.getMessage() );
    	}
    }

    // Verify that merging of subgraphs works for graphml/mergeSubgraphs_02.
    public void test_mergeSubgraphs_02()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/mergeSubgraphs_02" );
	    	assertTrue( mbt.getGraph().getVertices().size() == 16 );
	    	assertTrue( mbt.getGraph().getEdges().size() == 55 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
    		fail( e.getMessage() );
    	}
    }
    
    // Test merging of 2 simple graphs
    public void testMergeSimpleGraph()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test01" );
	    	assertTrue( mbt.getGraph().getEdges().size() == 9 );
	    	assertTrue( mbt.getGraph().getVertices().size() == 7 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
    		fail( e.getMessage() );
    	}
    }
    
    // Test merging of 2 simple graphs, with  nodes containing key word NO_MERGE
    public void testMergeSimpleGraph_Keyword_NO_MERGE()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test02" );
	    	assertTrue( mbt.getGraph().getEdges().size() == 11 );
	    	assertTrue( mbt.getGraph().getVertices().size() == 8 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
    		fail( e.getMessage() );
    	}
    }
    
    // Test merging a folder consisting 162 graphs
    public void testMergeLargeGraph()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test03" );
	    	assertTrue( mbt.getGraph().getEdges().size() == 1550 );
	    	assertTrue( mbt.getGraph().getVertices().size() == 788 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
    		fail( e.getMessage() );
    	}
    }

    // Verify that a graph containing a Stop vertex is correctly merged.
    public void testMergeSubGraphUsingStopVertex()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test22" );
	    	assertTrue( mbt.getGraph().getEdges().size() == 8 );
	    	assertTrue( mbt.getGraph().getVertices().size() == 8 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
	    	fail( e.getMessage() );
    	}
    }

    // Merging with subgraphs containing Stop vertices 
    public void testMergeSubGraphUsingStopVertices()
    {
    	try
    	{
	    	ModelBasedTesting mbt = new ModelBasedTesting();
	    	mbt.readGraph( "graphml/test23" );
	    	assertTrue( mbt.getGraph().getEdges().size() == 14 );
	    	assertTrue( mbt.getGraph().getVertices().size() == 9 );
	    	verifyIds( mbt.getGraph() );
    	}
    	catch ( Exception e)
    	{
    		System.err.println(e.getMessage());
	    	fail( e.getMessage() );
    	}
    }

    // Verify that all vertices and edges has indexes, and that no duplicates exists.
    private void verifyIds( SparseGraph g )
	{
		Object[] vertices1 = g.getVertices().toArray();
		for ( int i = 0; i < vertices1.length; i++ )
		{
			DirectedSparseVertex v1 = (DirectedSparseVertex)vertices1[ i ];
			int hits = 0;
			Integer index1 = (Integer)v1.getUserDatum( Keywords.INDEX_KEY );
	    	Object[] vertices2 = g.getVertices().toArray();
			for ( int j = 0; j < vertices1.length; j++ )
			{
				DirectedSparseVertex v2 = (DirectedSparseVertex)vertices2[ j ];
				Integer index2 = (Integer)v2.getUserDatum( Keywords.INDEX_KEY );
				if ( index1.intValue() == index2.intValue() )
				{
					hits++;
		    	}
	    	}
	    	assertTrue( hits == 1 );

	    	Object[] edges = g.getEdges().toArray();
			for ( int j = 0; j < edges.length; j++ )
			{
				DirectedSparseEdge e = (DirectedSparseEdge)edges[ j ];
				Integer index2 = (Integer)e.getUserDatum( Keywords.INDEX_KEY );
				if ( index1.intValue() == index2.intValue() )
				{
					hits++;
		    	}
	    	}
	    	assertTrue( hits == 1 );
		}					

		Object[] edges1 = g.getEdges().toArray();
		for ( int i = 0; i < edges1.length; i++ )
		{
			DirectedSparseEdge e1 = (DirectedSparseEdge)edges1[ i ];
			int hits = 0;
			Integer index1 = (Integer)e1.getUserDatum( Keywords.INDEX_KEY );
	    	Object[] edges2 = g.getEdges().toArray();
			for ( int j = 0; j < edges2.length; j++ )
			{
				DirectedSparseEdge e2 = (DirectedSparseEdge)edges2[ j ];
				Integer index2 = (Integer)e2.getUserDatum( Keywords.INDEX_KEY );
				if ( index1.intValue() == index2.intValue() )
				{
					hits++;
		    	}
	    	}
	    	assertTrue( hits == 1 );

	    	Object[] vertices2 = g.getVertices().toArray();
			for ( int j = 0; j < vertices1.length; j++ )
			{
				DirectedSparseVertex v2 = (DirectedSparseVertex)vertices2[ j ];
				Integer index2 = (Integer)v2.getUserDatum( Keywords.INDEX_KEY );
				if ( index1.intValue() == index2.intValue() )
				{
					hits++;
		    	}
	    	}
	    	assertTrue( hits == 1 );
		}					
	}
}