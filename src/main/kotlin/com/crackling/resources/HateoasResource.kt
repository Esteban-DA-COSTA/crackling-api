package com.crackling.resources

import sun.awt.image.ImageWatched

interface HateoasResource {
    val links: Map<String, String>
    
    fun generateSelfLink()
    fun generateParentLink()
}