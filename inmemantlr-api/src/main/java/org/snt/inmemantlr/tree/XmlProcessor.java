package org.snt.inmemantlr.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.inmemantlr.exceptions.AstProcessorException;

public class XmlProcessor extends AstProcessor<StringBuilder, StringBuilder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProcessor.class);

    /**
     * constructor
     *
     * @param ast abstract syntax tree to process
     */
    public XmlProcessor(Ast ast) {
        super(ast);
    }

    @Override
    public StringBuilder getResult() {
        StringBuilder root = smap.get(ast.getRoot());
        root.insert(0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        return root;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void process(AstNode n) throws AstProcessorException {

        StringBuilder sb = new StringBuilder();

        if(!n.hasParent()){
            simpleProp(n);
            return;
        }

        sb.append("<nt>");
        sb.append("<name>");
        sb.append(n.getRule());
        sb.append("</name>");
        sb.append("<ran>");
        sb.append(n.getSidx());
        sb.append(",");
        sb.append(n.getEidx());
        sb.append("</ran>");



        if (n.hasChildren()) {
            sb.append("<cld>");
            for (int i = 0; i < n.getChildren().size(); i++) {
                sb.append(smap.get(n.getChild(i)));
            }
            sb.append("</cld>");
        }

        sb.append("</nt>");

        smap.put(n, sb);
    }
}
