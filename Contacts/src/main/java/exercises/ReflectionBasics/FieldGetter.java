package exercises.ReflectionBasics;

/**
 Get number of fields class declares (the ones inherited should be excluded).
 */

/**
 * Declared fields
 * Implement getNumberOfFieldsClassDeclares method to count the number of fields declared in a class.
 * You should count public, protected, default (package) access, and private fields, excluding inherited fields.
 */


class FieldGetter {

    public int getNumberOfFieldsClassDeclares(Class<?> clazz) {
        return clazz.getDeclaredFields().length;
    }

}
