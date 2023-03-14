package oleart.nil.rickandmorty.base

open class Mapper<M, P> {

    open fun transformFrom(entity: P): M {
        throw UnsupportedOperationException()
    }

    open fun transformFrom(entities: List<P>?): List<M>? {
        val list: MutableList<M> = ArrayList()
        if (entities == null) {
            return null
        }
        for (entity in entities) {
            list.add(transformFrom(entity))
        }
        return list
    }

    open fun transformTo(entity: M): P {
        throw UnsupportedOperationException()
    }

    open fun transformTo(entities: List<M>): List<P> {
        val list: MutableList<P> = ArrayList()
        for (entity in entities) {
            list.add(transformTo(entity))
        }
        return list
    }
}