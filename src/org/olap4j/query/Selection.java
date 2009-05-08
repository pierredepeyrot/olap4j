/*
// $Id$
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// Copyright (C) 2007-2008 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package org.olap4j.query;

import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Member;

/**
 * A selection of members from an OLAP dimension hierarchy.
 *
 * <p>Concrete subclasses of this represent a real selection.
 * Selections include things such as 'children of', 'siblings of',
 * 'descendents of' etc.
 *
 * <p>This class is different from a {@link org.olap4j.metadata.Member} because it represents an
 * abstract member selection (e.g. children of widget' that may not represent
 * any members whereas a Member represents a single member that is known to
 * exist.
 *
 * @author jdixon, jhyde
 * @version $Id$
 * @since May 30, 2007
 */
public interface Selection {
    String getName();

    void setName(String name);

    Member getMember();

    Dimension getDimension();

    String getHierarchyName();

    String getLevelName();

    Operator getOperator();

    // @pre operator != null
    void setOperator(Operator operator);

    public enum Operator {
        MEMBER, CHILDREN, INCLUDE_CHILDREN, SIBLINGS, ANCESTORS, DESCENDANTS;
    }
}

// End Selection.java
